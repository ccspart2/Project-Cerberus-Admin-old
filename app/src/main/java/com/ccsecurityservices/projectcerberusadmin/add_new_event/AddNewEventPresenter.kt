package com.ccsecurityservices.projectcerberusadmin.add_new_event

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddNewEventPresenter(private val view: AddNewEventView) :
    AddNewEventContract.AddNewEventPresenter {

    private lateinit var eventname: String
    private lateinit var eventDate: LocalDate
    private lateinit var eventTime: LocalTime
    private lateinit var selectedLocation: SecLocation
    private lateinit var locationList: MutableList<SecLocation>
    private lateinit var eventDuration: String
    private var eventHeadcount: Int = 0

    private val fireBaseDatabase = FirebaseDatabase.getInstance()

    override fun getLocationsFromFireBase() {

        view.showLoading(true)
        val locationsReference = fireBaseDatabase.reference.child("locations")

        this.locationList = mutableListOf()

        val locationListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    val loc = dataSnapshot1.getValue(SecLocation::class.java)!!
                    locationList.add(loc)
                }
                view.showLoading(false)
                view.populateLocationSpinner(prepareListForSpinner())
            }
        }
        locationsReference.addListenerForSingleValueEvent(locationListener)
    }

    private fun prepareListForSpinner(): List<String> {
        val result = this.locationList.map { it.name }.toMutableList()
        result.add(0, "--Select a Location--")
        return result.toList()
    }

    override fun setSelectedLocation(position: Int) {
        this.selectedLocation = locationList[position]
    }

    override fun setDate(year: Int, month: Int, day: Int): String {
        val date = LocalDate.parse("$month/$day/$year", DateTimeFormatter.ofPattern("M/d/y"))
        this.eventDate = date
        return date.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"))
    }

    override fun setTime(hour: Int, minute: Int): String {
        val time = LocalTime.parse("$hour:$minute:00", DateTimeFormatter.ofPattern("H:m:ss"))
        this.eventTime = time
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }
}