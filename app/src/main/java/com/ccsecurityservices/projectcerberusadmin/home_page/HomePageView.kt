package com.ccsecurityservices.projectcerberusadmin.home_page

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_all_employees.SeeAllEmployeesView
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.see_all_events.SeeAllEventsView
import com.ccsecurityservices.projectcerberusadmin.see_all_locations.SeeAllLocationsView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_page.*

class HomePageView : AppCompatActivity(), HomePageContract.HomePageView {

    companion object {

        private const val RC_SIGN_IN = 123
    }

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
        home_page_log_out_BTN.setOnClickListener {
            logOut()
        }

        homePagePresenter.checkIfLoggedIn()
    }

    private fun logOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startLogInFlow()
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

    override fun startLogInFlow() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.cerberus_logo_color)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                homePagePresenter.checkIfValidAdmin(response!!)
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
