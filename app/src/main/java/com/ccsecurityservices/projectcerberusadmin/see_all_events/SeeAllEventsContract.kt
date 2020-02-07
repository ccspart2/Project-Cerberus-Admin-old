package com.ccsecurityservices.projectcerberusadmin.see_all_events

import com.ccsecurityservices.projectcerberusadmin.data_items.Event

interface SeeAllEventsContract {
    interface SeeAllEventsView {
        fun updateList()
        fun navToEventDetails(event: Event)
        fun displayLoading(state: Boolean)
    }

    interface SeeAllEventsPresenter {
        fun getEventList()
        fun detachListener()
    }
}