package com.ccsecurityservices.projectcerberusadmin.add_new_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatSpinner
import com.ccsecurityservices.projectcerberusadmin.R

class AddNewEventView : AppCompatActivity(), AddNewEventContract.AddNewEventView {

    private lateinit var pickDateBTN: AppCompatButton
    private lateinit var pickTimeBTN: AppCompatButton
    private lateinit var locationSpinner: AppCompatSpinner
    private lateinit var durationSeekBar: AppCompatSeekBar
    private lateinit var headcountTextView: AppCompatEditText
    private lateinit var addEmployeesBTN: AppCompatButton
    private lateinit var addEventBTN: AppCompatButton

    private lateinit var presenter: AddNewEventPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_event)

        presenter = AddNewEventPresenter(this)

        locationSpinner = findViewById(R.id.add_event_location_spinner)
        setOnClickListeners()
        presenter.getLocationsFromFireBase()

    }

    private fun setOnClickListeners() {

        //TODO: Modify the Spinner onClickListener, 
        // we have to make a default choice and add it to the list
        locationSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@AddNewEventView,
                    presenter.getLocationItem(position).name, Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


    override fun populateLocationSpinner(locationNames: List<String>) {
        if (locationNames.count() != 0) {
            val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, locationNames)
            locationSpinner.adapter = adapter
        }
    }
}
