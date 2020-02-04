package com.ccsecurityservices.projectcerberusadmin.add_new_event

import java.time.LocalDate

interface AddNewEventContract {
    interface AddNewEventView {
        fun populateLocationSpinner(locationNames: List<String>)
        fun showLoading(state: Boolean)
    }

    interface AddNewEventPresenter {
        fun getLocationsFromFireBase()
        fun setSelectedLocation(position: Int)
        fun setDate(year: Int, month: Int, day: Int): String
        fun setTime(hour: Int, minute: Int): String
    }
}