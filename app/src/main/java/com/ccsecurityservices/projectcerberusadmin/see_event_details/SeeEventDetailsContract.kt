package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Event

interface SeeEventDetailsContract {
    interface SeeEventDetailsView {
        fun populateFields(ev: Event, locName: String)
        fun showLoading(state: Boolean)
        fun disableBTNs()
        fun navBack()
        fun navToEventStatus(currentEvent: Event)
    }

    interface SeeEventDetailsPresenter {
        fun retrieveEventObject(intent: Intent)
        fun prepareDelete()
        fun setupNavToEventStatus()
    }
}