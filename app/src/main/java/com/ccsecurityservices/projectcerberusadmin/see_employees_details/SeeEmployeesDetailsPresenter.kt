package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

    override fun retrieveEmployeeObject(EMP: Employee) {
        this.currentEmployee = EMP
        view.populateFields(currentEmployee)

        if (currentEmployee.photoId != "") {
            view.showLoading(true)
            view.downloadPic(currentEmployee.photoId)
            view.showLoading(false)
        }
    }

    override fun deleteEmployee() {
        if (!this.currentEmployee.adminRights) {
            deleteProfilePic()
            deleteEmployeeDBEntry()
        } else {
            view.showToastMessages("The employee is an administrator. Please confirm with leadership for approval.")
        }
    }

    override fun prepareForEdit() {
        if (!this.currentEmployee.adminRights) {
            view.navToEditEmployee(this.currentEmployee)
        } else {
            view.showToastMessages("The employee is an administrator. Please confirm with leadership for approval.")
        }
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
                view.showToastMessages("The Profile Picture was Successfully Uploaded.")
                view.showLoading(false)
            } else {
                view.showToastMessages("An Error occurred trying to upload. Please try later.")
            }
        }
        view.updateProfilePicFromPicker(selectedImageUri)
    }
}