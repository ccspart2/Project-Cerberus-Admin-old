package com.ccsecurityservices.projectcerberusadmin.see_all_events

import com.ccsecurityservices.projectcerberusadmin.data_items.Event

interface SeeAllEventsContract {
    interface SeeAllEventsView {
        fun updateList()
        fun navToEventDetails(event: Event)
        fun displayLoading(state: Boolean)
        fun displayPopUpMessage(title: String, msg: String)
    }

    interface SeeAllEventsPresenter {
        fun getEventList()
        fun detachListener()
        fun refreshEvents()
    }
}