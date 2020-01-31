package com.ccsecurityservices.projectcerberusadmin.add_new_location

import android.content.Intent

interface AddNewLocationContract {
    interface AddNewLocationView {
        fun navBackSeeAllLocations()
        fun showFailMessage(msg : String)
    }

    interface AddNewLocationPresenter {
        fun addLocation(
            name: String,
            entrances: String,
            positions: String,
            suggestedCount: String
        )
        fun savePhotoIntent(data: Intent?)
        fun prepareIntentForProfilePic(): Intent
    }
}