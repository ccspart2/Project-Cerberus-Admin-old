package com.ccsecurityservices.projectcerberusadmin.see_employees_attendance

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.FirebaseDatabase

class SeeEmployeeAttendancePresenter(private val view: SeeEmployeeAttendanceView) :
    SeeEmployeeAttendanceContract.SeeEmployeeAttendancePresenter {

    private var items: MutableList<Event> = mutableListOf()
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var currentEmployee: Employee

    fun numberOfItems(): Int {
        return items.size
    }

    fun getEvent(position: Int): Event? {
        return items[position]
    }

    override fun retrieveFromIntent(intent: Intent) {
        this.currentEmployee = intent.extras!!.get("employee_event_attendance") as Employee
        view.populateView("${currentEmployee.firstName} ${currentEmployee.lastName}")
    }

    override fun getEventList() {
    }
}