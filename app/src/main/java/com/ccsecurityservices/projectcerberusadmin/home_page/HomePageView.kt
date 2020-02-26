package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_all_employees.SeeAllEmployeesView
import com.ccsecurityservices.projectcerberusadmin.see_all_events.SeeAllEventsView
import com.ccsecurityservices.projectcerberusadmin.see_all_locations.SeeAllLocationsView
import com.ccsecurityservices.projectcerberusadmin.sign_in.SignInView
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.home_page.*

class HomePageView : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var presenter: HomePagePresenter
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        imageView = findViewById(R.id.home_page_current_employee_profile_pic)

        presenter = HomePagePresenter(this)
        presenter.retrieveUserInfo()

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

    override fun populateUser(name: String, photoURL: String) {
        home_page_current_employee_name_label.text = name

        if (photoURL.trim().isNotEmpty()) {
            Glide.with(this).load(photoURL).into(imageView)
        }
    }

    override fun navToSignIn() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(this) {
            startActivity(Intent(this, SignInView::class.java))
            finish()
        }
    }
}
