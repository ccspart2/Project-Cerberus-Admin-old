package com.ccsecurityservices.projectcerberusadmin.see_location_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation

interface SeeLocationDetailsContract {
    interface SeeLocationDetailsView{

        fun populateFields(LOC: SecLocation)
        fun downloadProfilePic(imageUrl: String)
        fun deletionProcessResult(msg : String, result : Boolean)
        fun showLoading(state : Boolean)
    }
    interface SeeLocationDetailsPresenter{
        fun retrieveLocationObject(intent: Intent)
        fun startDeleteLocationProcess()
    }
}