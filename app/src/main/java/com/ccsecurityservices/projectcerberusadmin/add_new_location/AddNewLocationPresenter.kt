package com.ccsecurityservices.projectcerberusadmin.add_new_location

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.helper_classes.InputValidation
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddNewLocationPresenter(private val view: AddNewLocationView) :
    AddNewLocationContract.AddNewLocationPresenter {

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

    private lateinit var locName: String
    private var locEntrance = 0
    private var locPositions = 0
    private var locSuggested = 0
    private var locPhotoId = ""

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
            uploadLocationToFireBase(loc)
        } else {
            view.showFailMessage()
        }
    }

    private fun uploadLocationToFireBase(loc: SecLocation) {
        val dbReference = mDatabase.reference
        val locId = dbReference.push().key
        loc.id = locId!!
        dbReference.child("locations").child(locId).setValue(loc).addOnCompleteListener(view) {
            view.navBackSeeAllLocations()
        }
    }
}