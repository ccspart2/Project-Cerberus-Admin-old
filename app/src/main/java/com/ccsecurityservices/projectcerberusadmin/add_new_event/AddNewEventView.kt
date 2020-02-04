package com.ccsecurityservices.projectcerberusadmin.add_new_event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatSpinner
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.add_new_event.*


class AddNewEventView : AppCompatActivity(), AddNewEventContract.AddNewEventView {

    private lateinit var pickDateBTN: AppCompatButton
    private lateinit var pickTimeBTN: AppCompatButton
    private lateinit var locationSpinner: AppCompatSpinner
    private lateinit var durationSeekBar: AppCompatSeekBar
    private lateinit var headcountTextView: AppCompatEditText
    private lateinit var addEmployeesBTN: AppCompatButton
    private lateinit var addEventBTN: AppCompatButton

    private lateinit var descriptionEditText: AppCompatEditText

    private lateinit var presenter: AddNewEventPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_event)


        presenter = AddNewEventPresenter(this)
        setWidgets()
        presenter.getLocationsFromFireBase()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setWidgets() {

        val seek = findViewById<SeekBar>(R.id.add_event_duration_seekBar)
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {
                add_event_duration_seekBar_indicator_label.text = seek.progress.toString()
            }
        })

        //Setting up the Description text EditText Scroll feature.
        descriptionEditText = findViewById(R.id.add_event_description_edit_text)
        descriptionEditText.setOnTouchListener { _, _ ->
            add_event_scroll_view.requestDisallowInterceptTouchEvent(true)
            false
        }

        //Setting up Location Spinner Widget
        locationSpinner = findViewById(R.id.add_event_location_spinner)
        locationSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    presenter.setSelectedLocation(position - 1)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun populateLocationSpinner(locationNames: List<String>) {
        if (locationNames.count() != 0) {
            val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, locationNames)
            locationSpinner.adapter = adapter
        }
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            add_event_loading_widget.visibility = View.VISIBLE
        } else {
            add_event_loading_widget.visibility = View.GONE
        }
    }
}
