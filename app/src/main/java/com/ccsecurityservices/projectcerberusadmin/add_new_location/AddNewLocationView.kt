package com.ccsecurityservices.projectcerberusadmin.add_new_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.R
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
    }

    override fun navBackSeeAllLocations() {
        Toast.makeText(
            this,
            "The Location was successfully created.",
            Toast.LENGTH_LONG
        ).show()
        finish()
    }

    override fun showFailMessage() {
        Toast.makeText(
            this,
            "Some of the credentials are not valid. Please verify and try again",
            Toast.LENGTH_LONG
        ).show()
    }
}
