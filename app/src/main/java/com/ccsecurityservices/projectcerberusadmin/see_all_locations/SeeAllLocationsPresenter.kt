package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import com.ccsecurityservices.projectcerberusadmin.Data_Items.DummyLocations
import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation
import com.google.firebase.database.*

class SeeAllLocationsPresenter(private val view: SeeAllLocationsView) :
    SeeAllLocationsContract.SeeAllLocationsPresenter {

    private var items: MutableList<SecLocation> = mutableListOf()
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var locationsReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    fun numberOfItems(): Int {
        return items.size
    }

    fun getLocation(position: Int): SecLocation? {
        return items[position]
    }

    private fun sortItems() {
        items.sortBy { it.name }
    }

    fun setupNavToLocationDetails(currentLocation: SecLocation?) {
        view.navToLocationDetails(currentLocation!!)
    }

    override fun getLocationList() {

        //Dummy for testing
        items = DummyLocations.dLocations.toMutableList()
        view.updatedList()
        view.showLoding(false)

        locationsReference = mFireBaseDatabase.reference.child("locations")
        mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedLoc = p0.getValue(SecLocation::class.java)
                val oldEMP = items.find { it.id == changedLoc!!.id }
                items.remove(oldEMP)
                items.add(changedLoc!!)
                sortItems()
                view.updatedList()
                view.showLoding(false)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val loc = p0.getValue(SecLocation::class.java)
                items.add(loc!!)
                sortItems()
                view.updatedList()
                view.showLoding(false)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val deletedId = p0.getValue(SecLocation::class.java)!!.id
                val item = items.find { it.id == deletedId }
                items.remove(item)
                view.updatedList()
                view.showLoding(false)
            }

        }
        locationsReference.addChildEventListener(mChildEventListener)
    }

    override fun detachListener() {
        locationsReference.removeEventListener(mChildEventListener)
    }
}