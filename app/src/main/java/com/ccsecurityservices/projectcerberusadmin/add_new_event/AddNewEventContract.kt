package com.ccsecurityservices.projectcerberusadmin.add_new_event

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Event

interface AddNewEventContract {
    interface AddNewEventView {
        fun populateLocationSpinner(locationNames: List<String>)
        fun showLoading(state: Boolean)
        fun navToInviteEmployee(event: Event)
        fun displayToast(msg : String)
        fun displayCheckBox()
        fun navToSeeAllEvents()
    }

    interface AddNewEventPresenter {
        fun getLocationsFromFireBase()
        fun setSelectedLocation(position: Int)
        fun setDate(year: Int, month: Int, day: Int): String
        fun setTime(hour: Int, minute: Int): String
        fun setDuration(duration: Int)
        fun createEventObjectForInvitations(name: String, headCount: Int, description: String)
        fun getAttendanceListFromIntent(intent : Intent)
        fun checkCompleteEvent(name: String, headCount: Int, description: String)
    }
}