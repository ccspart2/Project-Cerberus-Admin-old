package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.add_new_location.AddNewLocationView
import com.ccsecurityservices.projectcerberusadmin.see_location_details.SeeLocationDetailsView
import kotlinx.android.synthetic.main.see_all_locations.*
import java.io.Serializable

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

        see_all_locations_add_BTN.setOnClickListener {
            val navIntent = Intent(this, AddNewLocationView::class.java)
            startActivity(navIntent)
        }
    }

    override fun updatedList() {
        adapter.notifyDataSetChanged()
    }

    override fun navToLocationDetails(loc: SecLocation) {
        val navIntent = Intent(this, SeeLocationDetailsView::class.java)
        navIntent.putExtra("location_details", loc as Serializable)
        startActivity(navIntent)
    }

    override fun showLoding(state: Boolean) {
        if (state) {
            see_all_locations_loading_widget.visibility = View.VISIBLE
        } else {
            see_all_locations_loading_widget.visibility = View.GONE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        seeAllLocationsPresenter.detachListener()
    }
}
