package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_all_locations.*

class SeeAllLocationsView : AppCompatActivity(), SeeAllLocationsContract.SeeAllLocationsView {

    private lateinit var seeAllLocationsPresenter: SeeAllLocationsPresenter
    private lateinit var adapter: SeeAllLocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_locations)

        seeAllLocationsPresenter = SeeAllLocationsPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        see_all_locations_RV.layoutManager = layoutManager

        adapter = SeeAllLocationsAdapter(seeAllLocationsPresenter)
        see_all_locations_RV.adapter = adapter

        seeAllLocationsPresenter.getLocationList()
    }

    override fun updatedList() {
        adapter.notifyDataSetChanged()
    }

    override fun navToLocationDetails(loc: SecLocation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoding(state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
