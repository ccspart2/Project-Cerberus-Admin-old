package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import android.content.ContentValues.TAG
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.SecLocation
import com.google.firebase.database.*

class SeeAllLocationsPresenter(private val view: SeeAllLocationsView) :
    SeeAllLocationsContract.SeeAllLocationsPresenter {

    private var items: MutableList<SecLocation> = mutableListOf()
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var locationsReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    fun numberOfItems(): Int {
        if(items.size == 0)
        {
            view.displayLoading(false)
        }
        return items.size
    }

    fun getLocation(position: Int): SecLocation? {
        return items[position]
    }

    fun setupNavToLocationDetails(currentLocation: SecLocation?) {
        view.navToLocationDetails(currentLocation!!)
    }

    private fun sortItems() {
        items.sortBy { it.name }
    }

    override fun getLocationList() {
        locationsReference = mFireBaseDatabase.reference.child("locations")
        mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedLoc = p0.getValue(SecLocation::class.java)
                val oldLoc = items.find { it.id == changedLoc!!.id }
                items.remove(oldLoc)
                items.add(changedLoc!!)
                sortItems()
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val loc = p0.getValue(SecLocation::class.java)
                items.add(loc!!)
                sortItems()
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val deletedId = p0.getValue(SecLocation::class.java)!!.id
                val item = items.find { it.id == deletedId }
                items.remove(item)
                view.updateList()
                view.displayLoading(false)
            }
        }
        locationsReference.addChildEventListener(mChildEventListener)
    }

    override fun detachListener() {
        locationsReference.removeEventListener(mChildEventListener)
    }
}