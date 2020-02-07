package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation

interface SeeAllLocationsContract {

    interface SeeAllLocationsView {
        fun updateList()
        fun navToLocationDetails(loc: SecLocation)
        fun displayLoading(state: Boolean)
    }

    interface SeeAllLocationsPresenter {
        fun getLocationList()
        fun detachListener()
    }
}