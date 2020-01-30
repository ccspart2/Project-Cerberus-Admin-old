package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.R

class SeeAllLocationsView : AppCompatActivity(), SeeAllLocationsContract.SeeAllLocationsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_locations)
    }

    override fun updatedList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navToLocationDetails(loc: SecLocation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoding(state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
