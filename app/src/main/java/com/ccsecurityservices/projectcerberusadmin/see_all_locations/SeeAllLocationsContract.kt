package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation

interface SeeAllLocationsContract {

    interface SeeAllLocationsView {
        fun updatedList()
        fun navToLocationDetails(loc: SecLocation)
        fun showLoding(state: Boolean)
    }

    interface SeeAllLocationsPresenter {
        fun getLocationList()
        fun detachListener()
    }
}