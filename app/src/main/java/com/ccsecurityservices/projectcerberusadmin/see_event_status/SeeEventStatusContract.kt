package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.content.Intent

interface SeeEventStatusContract {
    interface SeeEventStatusView {
        fun populateActivityLabel(eventName: String)
        fun updateList()
    }

    interface SeeEventStatusPresenter {
        fun retrieveEventObject(intent: Intent)
    }
}