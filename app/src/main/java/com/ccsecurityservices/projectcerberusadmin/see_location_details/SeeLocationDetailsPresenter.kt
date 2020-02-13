package com.ccsecurityservices.projectcerberusadmin.see_location_details

import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class SeeLocationDetailsPresenter(private val view: SeeLocationDetailsView) :
    SeeLocationDetailsContract.SeeLocationDetailsPresenter {

    private lateinit var currentLocation: SecLocation
    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun retrieveLocationObject(intent: Intent) {
        this.currentLocation = intent.extras!!.get("location_details") as SecLocation

        view.populateFields(this.currentLocation)

        if (this.currentLocation.photoId!!.isNotEmpty()) {
            view.showLoading(true)
            view.downloadProfilePic(this.currentLocation.photoId!!)
            view.showLoading(false)
        }
    }

    override fun startDeleteLocationProcess() {

        val events: MutableList<Event> = mutableListOf()
        val eventReference = mFireBaseDatabase
            .reference
            .child("events/active")

        val eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("SeeLocationDetailsPresenter", p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    events.add(it.getValue(Event::class.java)!!)
                }
                if (!checkIfActive(events)) {
                    view.showLoading(true)
                    if (currentLocation.photoId!!.isNotEmpty()) {
                        deleteLocationPicture()
                    } else {
                        deleteLocationDBEntry()
                    }
                } else {
                    view.displayActiveLocationDialog()
                }
            }
        }
        eventReference.addListenerForSingleValueEvent(eventListener)
    }

    private fun checkIfActive(events: MutableList<Event>): Boolean {
        events.forEach {
            if (it.locationId == this.currentLocation.id) {
                return true
            }
        }
        return false
    }

    private fun deleteLocationDBEntry() {
        val currentEmployeeReference = mFireBaseDatabase.reference
            .child("locations")
            .child(this.currentLocation.id)
        currentEmployeeReference.removeValue().addOnSuccessListener(view) {
            view.showLoading(false)
            view.deletionProcessResult("The location was successfully deleted ", true)
        }
            .addOnFailureListener(view) {
                view.showLoading(false)
                view.deletionProcessResult(
                    "The Location could not be deleted. Please try again later",
                    false
                )
            }
    }

    private fun deleteLocationPicture() {
        val profilePicsReference = mFireBaseStorage.reference
            .child("locations_pictures")
            .child(this.currentLocation.name)
        profilePicsReference.delete().addOnCompleteListener(view) {
            deleteLocationDBEntry()
        }
            .addOnFailureListener(view) {
                view.showLoading(false)
                view.deletionProcessResult(
                    "The Location could not be deleted. Please try again later",
                    false
                )
            }
    }
}