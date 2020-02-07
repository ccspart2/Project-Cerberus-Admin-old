package com.ccsecurityservices.projectcerberusadmin.add_new_event

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddNewEventPresenter(private val view: AddNewEventView) :
    AddNewEventContract.AddNewEventPresenter {

    private lateinit var locationList: MutableList<SecLocation>
    private lateinit var invitedEmployeeList: MutableList<Employee>

    private var currentEvent = Event()
    private val fireBaseDatabase = FirebaseDatabase.getInstance()
    private val eventReference = fireBaseDatabase.reference.child("events")
    private var mStorage: FirebaseStorage = FirebaseStorage.getInstance()

    private val dateNow = LocalDate.now()
    private var promoIntent: Intent? = null

    override fun getLocationsFromFireBase() {

        view.showLoading(true)
        val locationsReference = fireBaseDatabase.reference.child("locations")

        this.locationList = mutableListOf()

        val locationListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    val loc = dataSnapshot1.getValue(SecLocation::class.java)!!
                    locationList.add(loc)
                }
                view.showLoading(false)
                view.populateLocationSpinner(prepareListForSpinner())
            }
        }
        locationsReference.addListenerForSingleValueEvent(locationListener)
    }

    private fun prepareListForSpinner(): List<String> {
        val result = this.locationList.map { it.name }.toMutableList()
        result.add(0, "--Select a Location--")
        return result.toList()
    }

    override fun setSelectedLocation(position: Int) {
        this.currentEvent.locationId = locationList[position].id
    }

    override fun setDate(year: Int, month: Int, day: Int): String {
        val date = LocalDate.parse("$month/$day/$year", DateTimeFormatter.ofPattern("M/d/y"))
        return if (date.isAfter(this.dateNow)) {
            this.currentEvent.eventDate = date.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"))
            this.currentEvent.eventDate
        } else {
            view.displayToast("Event has to be in the future.")
            "Pick Date"
        }
    }

    override fun setTime(hour: Int, minute: Int): String {
        val time = LocalTime.parse("$hour:$minute:00", DateTimeFormatter.ofPattern("H:m:ss"))
        this.currentEvent.eventTime = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
        return this.currentEvent.eventTime
    }

    override fun setDuration(duration: Int) {
        this.currentEvent.duration = duration
    }

    override fun createEventObjectForInvitations(
        name: String,
        headCount: Int,
        description: String
    ) {
        when {
            headCount <= 0 -> {
                view.displayToast("Headcount Cannot be zero.")

            }
            name.trim().isEmpty() -> {
                view.displayToast("Name Cannot be Empty or just spaces.")
            }
            else -> {
                this.currentEvent.name = name.trim()
                this.currentEvent.headcount = headCount
                this.currentEvent.description = description
                view.navToInviteEmployee(currentEvent)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAttendanceListFromIntent(intent: Intent) {
        this.invitedEmployeeList =
            intent.extras!!.get("event_invitation_complete") as MutableList<Employee>
        view.displayCheckBox()
    }

    override fun UploadEvent(name: String, headCount: Int, description: String) {
        this.currentEvent.name = name.trim()
        this.currentEvent.headcount = headCount
        this.currentEvent.description = description

        if (checkFields()) {
            view.showLoading(true)
            createAttendances()
            if (this.promoIntent != null) {
                uploadPromoToStorage()
            } else {
                addEventToFireBase()
            }
        } else {
            view.displayToast("There are Empty fields for this event or Employees have not been invited.")
        }
    }

    private fun uploadPromoToStorage() {
        val selectedImageUri = this.promoIntent!!.data
        val photoRef = mStorage.reference
            .child("event_promos")
            .child(this.currentEvent.name)
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
                this.currentEvent.photoId = task.result!!.toString()
                addEventToFireBase()

            } else {
                view.displayToast("An Error occurred trying to upload. Please try later.")
            }
        }
    }

    override fun prepareIntentForPromoPic(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    override fun savePromoIntent(intent: Intent) {
        this.promoIntent = intent
    }

    private fun createAttendances() {
        var tempAttendance: Attendance
        var tempId: String

        for (emp in this.invitedEmployeeList) {
            tempAttendance =
                Attendance("", emp.id, "Invited", LocalDateTime.now().toString(), "", "")
            tempId = this.eventReference.push().key!!
            tempAttendance.id = tempId
            this.currentEvent.attendanceList[tempId] = tempAttendance
            emp.attendanceList[tempId] = tempAttendance
        }
    }

    private fun addEventToFireBase() {
        val id = eventReference.push().key
        this.currentEvent.id = id!!
        eventReference.child(id).setValue(this.currentEvent).addOnCompleteListener(view) {
            addAttendanceToAllEmployeesInvited()
        }
    }

    private fun addAttendanceToAllEmployeesInvited() {
        val employeeReference = fireBaseDatabase.reference.child("employees")

        for (emp in this.invitedEmployeeList) {
            employeeReference.child(emp.id).setValue(emp)
        }
        view.showLoading(false      )
        view.navToSeeAllEvents()
    }

    private fun checkFields(): Boolean {
        var result = true

        if (this.currentEvent.name.trim().isEmpty()) {
            result = false
        }
        if (this.currentEvent.locationId.trim().isEmpty()) {
            result = false
        }
        if (this.currentEvent.eventDate.trim().isEmpty()) {
            result = false
        }
        if (this.currentEvent.eventTime.trim().isEmpty()) {
            result = false
        }
        if (this.currentEvent.duration == 0) {
            result = false
        }
        if (this.currentEvent.headcount == 0) {
            result = false
        }
        if (this.currentEvent.description.trim().isEmpty()) {
            result = false
        }
        return result
    }
}