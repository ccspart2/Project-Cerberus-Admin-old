package com.ccsecurityservices.projectcerberusadmin.add_new_event

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event.InviteEmployeesToEventView
import kotlinx.android.synthetic.main.add_new_event.*
import java.io.Serializable
import java.util.*

class AddNewEventView : AppCompatActivity(), AddNewEventContract.AddNewEventView {

    companion object {
        const val START_ACTIVITY_3_REQUEST_CODE = 0
    }

    private lateinit var locationSpinner: AppCompatSpinner
    private lateinit var descriptionEditText: AppCompatEditText
    private lateinit var presenter: AddNewEventPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_event)

        presenter = AddNewEventPresenter(this)
        setWidgetsAndButtons()
        presenter.getLocationsFromFireBase()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setWidgetsAndButtons() {

        //Setting up Date and Time Pickers
        val calendarReference = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            add_event_dateTime_date_BTN.text = presenter.setDate(year, month + 1, dayOfMonth)
        }
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            add_event_dateTime_time_BTN.text = presenter.setTime(hourOfDay, minute)
        }

        add_event_dateTime_date_BTN.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendarReference.get(Calendar.YEAR),
                calendarReference.get(Calendar.MONTH),
                calendarReference.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        add_event_dateTime_time_BTN.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                calendarReference.get(Calendar.HOUR_OF_DAY),
                calendarReference.get(Calendar.MINUTE),
                false
            ).show()
        }

        //Setting up Duration SeekBar
        val seek = findViewById<SeekBar>(R.id.add_event_duration_seekBar)
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {
                add_event_duration_seekBar_indicator_label.text = seek.progress.toString()
                presenter.setDuration(seek.progress)
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

        //Setting up Add Employees BTN
        add_event_inviteEmployee_BTN.setOnClickListener {
            if (add_event_name_Edit_Text.text.isNullOrEmpty() || add_event_headcount_edit_text.text.isNullOrEmpty()) {
                displayToast("Please fill Event Name and Headcount in order to invite employees ")

            } else {
                presenter.createEventObjectForInvitations(
                    add_event_name_Edit_Text.text.toString(),
                    add_event_headcount_edit_text.text.toString().toInt(),
                    add_event_description_edit_text.text.toString()
                )
            }
        }

        add_event_BTN.setOnClickListener {
            if (add_event_headcount_edit_text.text.toString().trim().isNotEmpty()) {
                presenter.checkCompleteEvent(
                    add_event_name_Edit_Text.text.toString(),
                    add_event_headcount_edit_text.text.toString().toInt(),
                    add_event_description_edit_text.text.toString()
                )
            } else {
                displayToast("There are empty fields for this event or employees have not been invited.")
            }
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

    override fun navToInviteEmployee(event: Event) {
        val navIntent = Intent(this, InviteEmployeesToEventView::class.java)
        navIntent.putExtra("initial_event", event as Serializable)
        startActivityForResult(navIntent, START_ACTIVITY_3_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_ACTIVITY_3_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getAttendanceListFromIntent(data!!)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun displayToast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun displayCheckBox() {
        add_event_inviteEmployee_BTN_check_mark_image_view.visibility = View.VISIBLE
    }
}
