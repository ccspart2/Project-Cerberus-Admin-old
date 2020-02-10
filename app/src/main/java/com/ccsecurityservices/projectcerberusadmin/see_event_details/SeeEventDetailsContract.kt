package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Event

interface SeeEventDetailsContract {
    interface SeeEventDetailsView {
        fun populateFields(ev: Event, locName: String)
        fun showLoading(state : Boolean)
    }

    interface SeeEventDetailsPresenter {
        fun retrieveEventObject(intent: Intent)
    }
}