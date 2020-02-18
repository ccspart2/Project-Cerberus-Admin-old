package com.ccsecurityservices.projectcerberusadmin.see_event_details

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.R.string.*
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.see_event_status.SeeEventStatusView
import kotlinx.android.synthetic.main.see_event_details.*
import java.io.Serializable

class SeeEventDetailsView : AppCompatActivity(), SeeEventDetailsContract.SeeEventDetailsView {

    private lateinit var presenter: SeeEventDetailsPresenter
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_event_details)

        imageView = findViewById(R.id.see_event_details_pic)

        presenter = SeeEventDetailsPresenter(this)
        presenter.retrieveEventObject(intent)

        see_event_details_delete_event_BTN.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Event")
            builder.setMessage("Are you sure you want to delete this event?")
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                presenter.prepareDelete()
            }
            builder.show()
        }

        see_event_details_event_status_BTN.setOnClickListener {
            presenter.setupNavToEventStatus()
        }
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

    override fun disableBTNs() {
        see_event_details_event_status_BTN.visibility = View.GONE
        see_event_details_delete_event_BTN.visibility = View.GONE
    }

    override fun navBack() {
        Toast.makeText(this, "The event has been deleted successfully", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun navToEventStatus(currentEvent: Event) {
        val navIntent = Intent(this, SeeEventStatusView::class.java)
        navIntent.putExtra("event_status", currentEvent as Serializable)
        startActivity(navIntent)
    }
}
