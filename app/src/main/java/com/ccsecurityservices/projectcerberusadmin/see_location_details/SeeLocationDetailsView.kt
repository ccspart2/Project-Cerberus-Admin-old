package com.ccsecurityservices.projectcerberusadmin.see_location_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import kotlinx.android.synthetic.main.see_location_details.*

class SeeLocationDetailsView : AppCompatActivity(),
    SeeLocationDetailsContract.SeeLocationDetailsView {

    private lateinit var presenter: SeeLocationDetailsPresenter
    private lateinit var locationImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_location_details)

        locationImageView = findViewById(R.id.see_location_details_image_view)

        presenter = SeeLocationDetailsPresenter(this)
        presenter.retrieveLocationObject(intent)

        see_location_details_erase_BTN.setOnClickListener {
            presenter.startDeleteLocationProcess()
        }
    }

    override fun populateFields(LOC: SecLocation) {
        see_locations_details_name_text_view.text =
            getString(R.string.see_generics_details_name_text_placeholder).plus(" ${LOC.name}")
        see_locations_details_entrances_text_view.text =
            getString(R.string.see_location_details_entrances_textView_text).plus(" ${LOC.entrances}")
        see_locations_details_positions_text_view.text =
            getString(R.string.see_location_details_positions_textView_text).plus(" ${LOC.positions}")
        see_locations_details_suggestedCount_text_view.text =
            getString(R.string.see_location_details_suggestedCount_textView_text).plus(" ${LOC.suggestedCount}")
    }

    override fun downloadProfilePic(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(locationImageView)
    }

    override fun deletionProcessResult(msg: String, result: Boolean) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        if (result) {
            finish()
        }
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            see_location_details_loading_widget.visibility = View.VISIBLE
        } else {
            see_location_details_loading_widget.visibility = View.GONE
        }
    }
}
