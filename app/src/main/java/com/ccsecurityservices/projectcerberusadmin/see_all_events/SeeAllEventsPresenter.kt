package com.ccsecurityservices.projectcerberusadmin.see_all_events

import android.content.ContentValues.TAG
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.*

class SeeAllEventsPresenter(private val view: SeeAllEventsView) :
    SeeAllEventsContract.SeeAllEventsPresenter {

    private var items: MutableList<Event> = mutableListOf()
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var eventsReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    fun numberOfItems(): Int {
        if (items.size == 0) {
            view.displayLoading(false)
        }
        return items.size
    }

    fun getEvent(position: Int): Event? {
        return items[position]
    }

    fun setupNavToEventDetails(currentEvent: Event?) {
        view.navToEventDetails(currentEvent!!)
    }

    override fun getEventList() {
        this.eventsReference = mFireBaseDatabase.reference.child("events")
        this.mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedEvent = p0.getValue(Event::class.java)
                val oldEvent = items.find { it.id == changedEvent!!.id }
                items.remove(oldEvent)
                items.add(changedEvent!!)
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val event = p0.getValue(Event::class.java)
                items.add(event!!)
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val deletedId = p0.getValue(Event::class.java)!!.id
                val item = items.find { it.id == deletedId }
                items.remove(item)
                view.updateList()
                view.displayLoading(false)
            }
        }
        eventsReference.addChildEventListener(mChildEventListener)
    }

    override fun detachListener() {
        this.eventsReference.removeEventListener(this.mChildEventListener)
    }
}