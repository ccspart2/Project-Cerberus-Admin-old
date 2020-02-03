package com.ccsecurityservices.projectcerberusadmin.add_new_event

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation

interface AddNewEventContract {
    interface AddNewEventView {
        fun populateLocationSpinner(locationNames: List<String>)

    }

    interface AddNewEventPresenter {
        fun getLocationsFromFireBase()
        fun getLocationItem(position : Int): SecLocation
    }
}