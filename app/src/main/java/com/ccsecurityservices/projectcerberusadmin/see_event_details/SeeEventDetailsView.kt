package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.R.string.*
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import kotlinx.android.synthetic.main.see_event_details.*

class SeeEventDetailsView : AppCompatActivity(), SeeEventDetailsContract.SeeEventDetailsView {

    private lateinit var presenter: SeeEventDetailsPresenter
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_event_details)

        imageView = findViewById(R.id.see_event_details_pic)

        presenter = SeeEventDetailsPresenter(this)
        presenter.retrieveEventObject(intent)
    }

    override fun populateFields(ev: Event, locName: String) {
        see_event_details_name_label.text = getString(see_generics_details_name_text_placeholder)
            .plus(" ${ev.name}")
        see_event_details_date_label.text = getString(see_event_details_date_label_text)
            .plus(" ${ev.eventDate}")
        see_event_details_time_label.text = getString(see_event_details_time_label_text)
            .plus(" ${ev.eventTime}")
        see_event_details_location_name_label.text =
            getString(see_event_details_location_name_label_text)
                .plus(" $locName")
        see_event_details_duration_label.text = getString(see_event_details_duration_label_text)
            .plus(" ${ev.duration} hours")
        see_event_details_headcount_label.text = getString(see_event_details_headcount_label_text)
            .plus(" ${ev.headcount} employees")

        if (ev.photoId.trim().isNotEmpty()) {
            Glide.with(this).load(ev.photoId).into(imageView)
            showLoading(false)
        } else {
            showLoading(false)
        }
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            see_event_details_loading_widget.visibility = View.VISIBLE
        } else {
            see_event_details_loading_widget.visibility = View.GONE
        }
    }
}
