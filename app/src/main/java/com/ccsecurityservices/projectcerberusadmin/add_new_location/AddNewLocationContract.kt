package com.ccsecurityservices.projectcerberusadmin.add_new_location

interface AddNewLocationContract {
    interface AddNewLocationView {
        fun navBackSeeAllLocations()
        fun showFailMessage()
    }

    interface AddNewLocationPresenter {
        fun addLocation(
            name: String,
            entrances: String,
            positions: String,
            suggestedCount: String
        )
    }
}