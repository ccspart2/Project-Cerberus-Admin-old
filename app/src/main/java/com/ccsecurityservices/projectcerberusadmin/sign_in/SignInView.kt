package com.ccsecurityservices.projectcerberusadmin.sign_in

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.home_page.HomePageView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_in_parent_layout.*

class SignInView : AppCompatActivity(), SignInContract.SignInView {

    companion object {

        private const val RC_SIGN_IN = 123
    }

    private lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_parent_layout)

        presenter = SignInPresenter(this)

        sign_in_parent_BTN.setOnClickListener {
            presenter.checkIfSignedIn()
        }
    }

    override fun navToHomePage() {
        displayLoading(false)
        startActivity(Intent(this, HomePageView::class.java))
        finish()
    }

    override fun startSignInFlow() {

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun invalidUserDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Invalid User")
        builder.setMessage("You are not enlisted to use Project Cerberus as an valid Admin. Please contact C&C Security Office in order to make the necessary arrangements.")
        builder.setNeutralButton("OK") { _: DialogInterface?, _: Int ->
            displayLoading(false)
        }

        AuthUI.getInstance()
            .delete(this).addOnCompleteListener {
                builder.show()
            }
    }

    override fun displayLoading(state: Boolean) {
        if (state) {
            sign_in_parent_loading_widget.visibility = View.VISIBLE
        } else {
            sign_in_parent_loading_widget.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                presenter.checkIfAdmin(FirebaseAuth.getInstance().currentUser!!)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
