package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class SeeEmployeesDetailsPresenter(private val view: SeeEmployeesDetailsView) :
    SeeEmployeesDetailsContract.SeeEmployeesDetailsPresenter {

    private lateinit var currentEmployee: Employee

    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var currentEmployeeReference: DatabaseReference

    private fun deleteProfilePic() {
        val profilePicsReference = mFireBaseStorage.reference
            .child("employees_profile_pictures")
            .child("${this.currentEmployee.firstName}.${this.currentEmployee.lastName}")
        profilePicsReference.delete()
    }

    private fun updateUrlInEmployeeRecord(photoURL: String) {
        currentEmployeeReference = mFireBaseDatabase.reference
            .child("employees")
            .child(currentEmployee.id)
            .child("photoId")

        currentEmployeeReference.setValue(photoURL)
    }

    private fun deleteEmployeeDBEntry() {
        currentEmployeeReference =
            mFireBaseDatabase.reference.child("employees").child(currentEmployee.id)
        currentEmployeeReference.removeValue().addOnSuccessListener(view) {
            view.deleteResult(true)
        }
    }

    private fun deleteEmployee() {
        if (this.currentEmployee.photoId.isNotEmpty()) {
            deleteProfilePic()
        }
        deleteEmployeeDBEntry()
    }

    private fun checkIfActive(events: MutableList<Event>): Boolean {
        events.forEach {
            it.attendanceList.forEach { att ->
                if (att.value.employeeId == this.currentEmployee.id) {
                    return true
                }
            }
        }
        return false
    }

    override fun retrieveEmployeeObject(intent: Intent) {
        this.currentEmployee = intent.extras!!.get("employee_details") as Employee
        view.populateFields(currentEmployee)

        if (currentEmployee.photoId != "") {
            view.showLoading(true)
            view.downloadPic(currentEmployee.photoId)
            view.showLoading(false)
        }
    }

    override fun prepareForDelete() {
        val events: MutableList<Event> = mutableListOf()
        val eventsReference = mFireBaseDatabase
            .reference
            .child("events/active")
        val eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("SeeEmployeesDetailsPresenter", p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    events.add(it.getValue(Event::class.java)!!)
                }
                if (!checkIfActive(events)) {
                    deleteEmployee()
                } else {
                    view.displayWarningDialog(
                        "Employee Active",
                        "This employee is currently invited to an active event. In order to erase, please un-invite from the active event"
                    )
                }
            }
        }
        eventsReference.addListenerForSingleValueEvent(eventListener)
    }

    override fun prepareForEdit() {
        view.navToEditEmployee(this.currentEmployee)
    }

    override fun createIntentForProfilePic(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    override fun retrieveProfilePic(data: Intent?) {
        view.showLoading(true)

        val selectedImageUri = data!!.data

        //Get a reference to store file at employees_profile_pictures/<FILENAME>
        val photoRef = mFireBaseStorage.reference
            .child("employees_profile_pictures")
            .child("${this.currentEmployee.firstName}.${this.currentEmployee.lastName}")

        val uploadTask = photoRef.putFile(selectedImageUri!!)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener(view) { task ->
            if (task.isSuccessful) {
                updateUrlInEmployeeRecord(task.result!!.toString())
                view.showToastMessages("The profile picture was successfully uploaded.")
                view.showLoading(false)
            } else {
                view.showToastMessages("An Error occurred trying to upload. Please try later.")
            }
        }
        view.updateProfilePicFromPicker(selectedImageUri)
    }

    override fun prepareNavToAttendanceActivity() {
        if (this.currentEmployee.attendanceList.values.isEmpty()) {
            view.displayWarningDialog(
                "No Attendance",
                "This Employee has not been invited to any event."
            )
        } else {
            view.navToEmployeeAttendance(this.currentEmployee)
        }
    }
}