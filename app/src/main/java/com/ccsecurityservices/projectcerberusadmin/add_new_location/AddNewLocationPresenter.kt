package com.ccsecurityservices.projectcerberusadmin.add_new_location

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.helper_classes.InputValidation
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddNewLocationPresenter(private val view: AddNewLocationView) :
    AddNewLocationContract.AddNewLocationPresenter {

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mStorage: FirebaseStorage = FirebaseStorage.getInstance()

    private lateinit var locName: String
    private var locEntrance = 0
    private var locPositions = 0
    private var locSuggested = 0
    private var locPhotoId = ""
    private var photoIntent: Intent? = null

    override fun addLocation(
        name: String,
        entrances: String,
        positions: String,
        suggestedCount: String
    ) {
        if (entrances.isNotEmpty() &&
            positions.isNotEmpty() &&
            suggestedCount.isNotEmpty() &&
            InputValidation.eventAndLocInputValidation(name.trim())
        ) {
            this.locName = name.trim()
            this.locEntrance = entrances.toInt()
            this.locPositions = positions.toInt()
            this.locSuggested = suggestedCount.toInt()

            val loc = SecLocation(
                "",
                this.locName,
                this.locEntrance,
                this.locPositions,
                this.locSuggested,
                this.locPhotoId
            )
            view.showLoading(true)
            if (this.photoIntent != null) {
                uploadLocationPhotoToStorage(loc)
            } else {
                uploadLocationToFireBase(loc)
            }
        } else {
            view.showFailMessage("Some of the credentials are not valid. Please verify and try again")
        }
    }

    private fun uploadLocationPhotoToStorage(loc: SecLocation) {

        val selectedImageUri = photoIntent!!.data

        val photoRef = mStorage.reference
            .child("locations_pictures")
            .child(this.locName)
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
                loc.photoId = task.result!!.toString()
                uploadLocationToFireBase(loc)

            } else {
                view.showFailMessage("An Error occurred trying to upload. Please try later.")
            }
        }
    }

    override fun savePhotoIntent(data: Intent?) {
        if (data != null) {
            this.photoIntent = data
        }
    }

    override fun prepareIntentForProfilePic(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    private fun uploadLocationToFireBase(loc: SecLocation) {
        val dbReference = mDatabase.reference
        val locId = dbReference.push().key
        loc.id = locId!!
        dbReference.child("locations").child(locId).setValue(loc).addOnCompleteListener(view) {
            view.showLoading(false)
            view.navBackSeeAllLocations()
        }
    }
}