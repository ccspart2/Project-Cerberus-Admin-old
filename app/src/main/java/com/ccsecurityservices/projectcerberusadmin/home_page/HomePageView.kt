package com.ccsecurityservices.projectcerberusadmin.home_page

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_all_employees.SeeAllEmployeesView
import com.ccsecurityservices.projectcerberusadmin.see_all_events.SeeAllEventsView
import com.ccsecurityservices.projectcerberusadmin.see_all_locations.SeeAllLocationsView
import com.ccsecurityservices.projectcerberusadmin.see_employees_details.SeeEmployeesDetailsView
import com.ccsecurityservices.projectcerberusadmin.sign_in.SignInView
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.home_page.*

class HomePageView : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var presenter: HomePagePresenter
    private lateinit var imageView: ImageView

    companion object {
        const val RC_PHOTO_PICKER = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        imageView = findViewById(R.id.home_page_current_employee_profile_pic)

        presenter = HomePagePresenter(this)
        displayLoadingWidget(true)
        presenter.retrieveEmployeeFromAuth()

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
        home_page_current_employee_profile_pic.setOnClickListener {
            startActivityForResult(
                Intent.createChooser(
                    presenter.createIntentForProfilePic(),
                    "Complete action using"
                ),
                SeeEmployeesDetailsView.RC_PHOTO_PICKER
            )
        }
    }

    private fun editProfilePicConfirmationDialog(data: Intent?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update profile Picture")
        builder.setMessage("Are you sure you want to change the profile picture?")
        builder.setPositiveButton("Yes") { _, _ ->
            presenter.updateProfilePicInStorage(data)
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
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
        displayLoadingWidget(false)
    }

    override fun navToSignIn() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(this) {
            startActivity(Intent(this, SignInView::class.java))
            finish()
        }
    }

    override fun updateImageView(selectedImageUri: Uri) {
        home_page_current_employee_profile_pic.setImageURI(selectedImageUri)
    }

    override fun displayToastMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun displayLoadingWidget(state: Boolean) {
        if (state) {
            home_page_loading_widget.visibility = View.VISIBLE
        } else {
            home_page_loading_widget.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            editProfilePicConfirmationDialog(data)
        }
    }
}
