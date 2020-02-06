package com.ccsecurityservices.projectcerberusadmin.add_new_event

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddNewEventPresenter(private val view: AddNewEventView) :
    AddNewEventContract.AddNewEventPresenter {

    private lateinit var locationList: MutableList<SecLocation>
    private var currentEvent = Event()
    private val fireBaseDatabase = FirebaseDatabase.getInstance()
    private val dateNow = LocalDate.now()

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
        this.currentEvent.attendanceList =
            intent.extras!!.get("event_invitation_complete") as MutableList<Attendance>
        view.displayCheckBox()
    }

    override fun checkCompleteEvent(name: String, headCount: Int, description: String) {

        this.currentEvent.name = name.trim()
        this.currentEvent.headcount = headCount
        this.currentEvent.description = description

        if (checkFields()) {
            addEventToFireBase()

        } else {
            view.displayToast("There are Empty fields for this event or Employees have not been invited.")
        }
    }

    private fun addEventToFireBase() {
        view.displayToast("Se puede!")
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
        if (this.currentEvent.attendanceList.size == 0) {
            result = false
        }
        return result
    }
}