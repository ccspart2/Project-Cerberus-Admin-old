package com.ccsecurityservices.projectcerberusadmin.see_all_events

import android.content.ContentValues.TAG
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SeeAllEventsPresenter(private val view: SeeAllEventsView) :
    SeeAllEventsContract.SeeAllEventsPresenter {

    private var items: MutableList<Event> = mutableListOf()
    private var allEvents: MutableList<Event> = mutableListOf()

    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var eventsReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    fun numberOfItems(): Int {
        return items.size
    }

    fun getEvent(position: Int): Event? {
        return items[position]
    }

    fun setupNavToEventDetails(currentEvent: Event?) {
        view.navToEventDetails(currentEvent!!)
    }

    private fun filterToActive() {
        items.clear()
        items = allEvents.filter { !it.eventPassed }.toMutableList()
    }

    override fun getEventList() {
        this.eventsReference = mFireBaseDatabase.reference.child("events/active")
        this.mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val changedEvent = p0.getValue(Event::class.java)
                val oldEvent = allEvents.find { it.id == changedEvent!!.id }
                allEvents.remove(oldEvent)
                allEvents.add(changedEvent!!)
                filterToActive()
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val event = p0.getValue(Event::class.java)
                allEvents.add(event!!)
                filterToActive()
                view.updateList()
                view.displayLoading(false)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val deletedId = p0.getValue(Event::class.java)!!.id
                val item = allEvents.find { it.id == deletedId }
                allEvents.remove(item)
                filterToActive()
                view.updateList()
                view.displayLoading(false)
            }
        }
        eventsReference.addChildEventListener(mChildEventListener)
    }

    override fun detachListener() {
        this.eventsReference.removeEventListener(this.mChildEventListener)
    }

    override fun refreshEvents() {
        val currentDay = LocalDate.now()

        eventsReference = mFireBaseDatabase.reference.child("events").child("past")
        val currentEventsReference = mFireBaseDatabase.reference.child("events").child("active")

        for (ev in this.allEvents) {
            val evDate = LocalDate.parse(ev.eventDate, DateTimeFormatter.ofPattern("dd MMM, yyyy"))
            if (evDate.isBefore(currentDay)) {
                ev.eventPassed = true
                eventsReference.child(ev.id).setValue(ev)
                currentEventsReference.child(ev.id).removeValue()
            }
        }
        view.displayPopUpMessage(
            "Your Events are Updated!",
            "This view will only show the currently active events. For more information " +
                    "about passed events, you can visit the employees and see their attendance."
        )
    }
}