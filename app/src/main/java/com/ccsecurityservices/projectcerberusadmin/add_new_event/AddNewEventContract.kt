package com.ccsecurityservices.projectcerberusadmin.add_new_event

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation

interface AddNewEventContract {
    interface AddNewEventView {
        fun populateLocationSpinner(locationNames: List<String>)
        fun showLoading(state: Boolean)
    }

    interface AddNewEventPresenter {
        fun getLocationsFromFireBase()
        fun setSelectedLocation(position: Int)
    }
}