package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.content.Intent

interface SeeEventStatusContract {
    interface SeeEventStatusView {
        fun populateActivityLabel(eventName: String)
        fun updateList()
        fun populateHeadcount(eventHeadCount: Int, currentHeadcount: Int)
        fun navBack()
        fun displayLoading(state : Boolean)
    }

    interface SeeEventStatusPresenter {
        fun retrieveData(intent: Intent)
        fun sortByAttendance()
        fun updateFireBaseDB()
    }
}