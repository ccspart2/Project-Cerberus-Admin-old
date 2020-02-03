package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.add_new_event.AddNewEvent
import com.ccsecurityservices.projectcerberusadmin.see_all_employees.SeeAllEmployeesView
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.see_all_locations.SeeAllLocationsView
import kotlinx.android.synthetic.main.home_page.*

class HomePageView : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var homePagePresenter: HomePagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        homePagePresenter = HomePagePresenter(this)

        home_page_employees_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
        home_page_locations_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
        home_page_events_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
    }

    private fun navigateOutHomePage(viewID: Int) {
        when (viewID) {
            R.id.home_page_employees_btn -> {
                val navIntent = Intent(this, SeeAllEmployeesView::class.java)
                startActivity(navIntent)
            }
            R.id.home_page_events_btn -> {

                //TODO Remember to change to SeeAllEvents
                val navIntent = Intent(this, AddNewEvent::class.java)
                startActivity(navIntent)
            }
            R.id.home_page_locations_btn -> {
                val navIntent = Intent(this, SeeAllLocationsView::class.java)
                startActivity(navIntent)
            }
        }
    }

    override fun populateUser(): Employee {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
