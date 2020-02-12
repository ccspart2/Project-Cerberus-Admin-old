package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SeeEventDetailsPresenter(private val view: SeeEventDetailsView) :
    SeeEventDetailsContract.SeeEventDetailsPresenter {

    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var currentEvent: Event
    private lateinit var currentLocation: SecLocation

    override fun retrieveEventObject(intent: Intent) {
        currentEvent = intent.extras!!.get("event_details") as Event
        fetchLocationName()
    }

    private fun fetchLocationName() {

        val locationReference = mFireBaseDatabase.reference
            .child("locations")
            .child(currentEvent.locationId)

        val locationListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(ContentValues.TAG, p0.message)
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
}