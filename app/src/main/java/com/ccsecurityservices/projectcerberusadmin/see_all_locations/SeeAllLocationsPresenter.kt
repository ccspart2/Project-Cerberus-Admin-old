package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import com.ccsecurityservices.projectcerberusadmin.Data_Items.DummyLocations
import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SeeAllLocationsPresenter(private val view: SeeAllLocationsView) :
    SeeAllLocationsContract.SeeAllLocationsPresenter {

    private var items: MutableList<SecLocation> = mutableListOf()
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var locationsReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener


    override fun getLocationList() {
        items = DummyLocations.dLocations.toMutableList()
        view.updatedList()
    }

    override fun detachListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun numberOfItems(): Int {
        return items.size
    }

    fun getLocation(position: Int): SecLocation? {
        return items[position]
    }

    fun setupNavToLocationDetails(currentLocation : SecLocation?){
        view.navToLocationDetails(currentLocation!!)
    }

}