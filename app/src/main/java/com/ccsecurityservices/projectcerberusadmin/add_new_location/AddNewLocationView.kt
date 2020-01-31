package com.ccsecurityservices.projectcerberusadmin.add_new_location

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_employees_details.SeeEmployeesDetailsView
import kotlinx.android.synthetic.main.add_new_location.*

class AddNewLocationView : AppCompatActivity(), AddNewLocationContract.AddNewLocationView {

    private lateinit var presenter: AddNewLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_location)

        presenter = AddNewLocationPresenter(this)

        add_location_BTN.setOnClickListener {
            presenter.addLocation(
                add_location_name_edit_text.text.toString(),
                add_location_entrances_edit_text.text.toString(),
                add_location_positions_edit_text.text.toString(),
                add_location_suggested_count_edit_text.text.toString()
            )
        }
        add_location_photo_BTN.setOnClickListener {
            val intent = presenter.prepareIntentForProfilePic()
            startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                SeeEmployeesDetailsView.RC_PHOTO_PICKER
            )
        }
    }

    override fun navBackSeeAllLocations() {
        Toast.makeText(
            this,
            "The Location was successfully created.",
            Toast.LENGTH_LONG
        ).show()
        finish()
    }

    override fun showFailMessage(msg: String) {
        Toast.makeText(
            this,
            "msg",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SeeEmployeesDetailsView.RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            presenter.savePhotoIntent(data)
        }
    }
}
