package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.see_all_employees.SeeAllEmployeesView
import com.ccsecurityservices.projectcerberusadmin.see_all_events.SeeAllEventsView
import com.ccsecurityservices.projectcerberusadmin.see_all_locations.SeeAllLocationsView
import com.ccsecurityservices.projectcerberusadmin.sign_in.SignInView
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.home_page.*

class HomePageView : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var presenter: HomePagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        presenter = HomePagePresenter(this)

        home_page_employees_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
        home_page_locations_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
        home_page_events_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
        home_page_logOut_BTN.setOnClickListener {
            presenter.logOut()
        }
    }

    private fun navigateOutHomePage(viewID: Int) {
        when (viewID) {
            R.id.home_page_employees_btn -> {
                val navIntent = Intent(this, SeeAllEmployeesView::class.java)
                startActivity(navIntent)
            }
            R.id.home_page_events_btn -> {
                val navIntent = Intent(this, SeeAllEventsView::class.java)
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

    override fun navToSignIn() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(this) {
            startActivity(Intent(this, SignInView::class.java))
            finish()
        }
    }
}
