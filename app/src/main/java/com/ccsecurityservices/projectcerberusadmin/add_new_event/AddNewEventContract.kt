package com.ccsecurityservices.projectcerberusadmin.add_new_event

import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import java.time.LocalDate

interface AddNewEventContract {
    interface AddNewEventView {
        fun populateLocationSpinner(locationNames: List<String>)
        fun showLoading(state: Boolean)
        fun navToInviteEmployee(event: Event)
        fun displayToast(msg : String)
    }

    interface AddNewEventPresenter {
        fun getLocationsFromFireBase()
        fun setSelectedLocation(position: Int)
        fun setDate(year: Int, month: Int, day: Int): String
        fun setTime(hour: Int, minute: Int): String
        fun setDuration(duration: Int)
        fun createEventObjectForInvitations(name: String, headCount: Int, description: String)
    }
}