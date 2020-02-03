package com.ccsecurityservices.projectcerberusadmin.add_new_event

import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalTime

class AddNewEventPresenter(private val view: AddNewEventView) :
    AddNewEventContract.AddNewEventPresenter {

    private lateinit var eventname: String
    private lateinit var eventDate: LocalDate
    private lateinit var eventTime: LocalTime
    private lateinit var locationList: MutableList<SecLocation>
    private lateinit var eventDuration: String
    private var eventHeadcount: Int = 0

    private val fireBaseDatabase = FirebaseDatabase.getInstance()

    override fun getLocationsFromFireBase() {

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
                view.populateLocationSpinner(locationList.map { it.name })
            }
        }
        locationsReference.addListenerForSingleValueEvent(locationListener)
    }

    override fun getLocationItem(position: Int): SecLocation {
        return this.locationList[position]
    }

}