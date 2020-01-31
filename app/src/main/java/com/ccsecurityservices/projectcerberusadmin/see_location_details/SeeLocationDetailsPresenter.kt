package com.ccsecurityservices.projectcerberusadmin.see_location_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.FirebaseDatabase
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

    //TODO: Recordar que tenemos que verificar
    // si este location esta siendo usado en un evento!
    override fun startDeleteLocationProcess() {
        view.showLoading(true)
        if (this.currentLocation.photoId!!.isNotEmpty()) {
            deleteLocationPicture()
        } else {
            deleteLocationDBEntry()
        }
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