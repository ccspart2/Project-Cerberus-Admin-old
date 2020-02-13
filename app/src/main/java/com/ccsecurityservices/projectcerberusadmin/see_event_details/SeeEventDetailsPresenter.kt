package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SeeEventDetailsPresenter(private val view: SeeEventDetailsView) :
    SeeEventDetailsContract.SeeEventDetailsPresenter {

    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

    private lateinit var currentEvent: Event
    private lateinit var currentLocation: SecLocation

    private fun fetchLocationName() {

        val locationReference = mFireBaseDatabase.reference
            .child("locations")
            .child(currentEvent.locationId)

        val locationListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("SeeEventDetailsPresenter", p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentLocation = dataSnapshot.getValue(SecLocation::class.java)!!
                view.populateFields(currentEvent, currentLocation.name)
                checkEventPassed()
            }
        }
        locationReference.addListenerForSingleValueEvent(locationListener)
    }

    private fun checkEventPassed() {
        val currentDay = LocalDate.now()
        val evDate =
            LocalDate.parse(currentEvent.eventDate, DateTimeFormatter.ofPattern("dd MMM, yyyy"))
        if (evDate.isBefore(currentDay)) {
            view.disableBTNs()
        }
    }

    private fun deletePhoto() {
        val profilePicsReference = mFireBaseStorage.reference
            .child("event_promos")
            .child(this.currentEvent.name)
        profilePicsReference.delete()
    }

    private fun deleteEvent() {
        val eventReference = mFireBaseDatabase.reference

        eventReference
            .child("events/active")
            .child(this.currentEvent.id)
            .removeValue().addOnCompleteListener(view) {
                view.navBack()
            }
    }

    private fun deleteAttendance(attendanceList: MutableMap<String, Attendance>) {
        val attendanceReference = mFireBaseDatabase.reference

        attendanceList.forEach { (key, obj) ->
            attendanceReference
                .child("employees")
                .child(obj.employeeId)
                .child("attendanceList")
                .child(key)
                .removeValue()
        }
    }

    override fun retrieveEventObject(intent: Intent) {
        currentEvent = intent.extras!!.get("event_details") as Event
        fetchLocationName()
    }

    override fun prepareDelete() {
        if (this.currentEvent.photoId.trim().isNotEmpty()) {
            deletePhoto()
        }
        deleteAttendance(this.currentEvent.attendanceList)
        deleteEvent()
    }
}