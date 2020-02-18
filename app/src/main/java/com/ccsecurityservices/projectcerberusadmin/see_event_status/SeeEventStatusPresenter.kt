package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeEventStatusPresenter(private val view: SeeEventStatusView) :
    SeeEventStatusContract.SeeEventStatusPresenter {

    private lateinit var currentEvent: Event
    private var employeeItems: MutableList<Employee> = mutableListOf()
    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun retrieveEventObject(intent: Intent) {
        this.currentEvent = intent.extras!!.get("event_status") as Event
        view.populateActivityLabel(currentEvent.name)
        retrieveEmployeeList()
    }

    private fun retrieveEmployeeList() {
        val employeesReference = mFireBaseDatabase.reference
            .child("employees")

        val employeesListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("SeeEventStatus", p0.message)
            }

            override fun onDataChange(dataSet: DataSnapshot) {
                dataSet.children.forEach {
                    employeeItems.add(it.getValue(Employee::class.java)!!)
                }
                view.updateList()
            }
        }
        employeesReference.addListenerForSingleValueEvent(employeesListener)
    }

    fun numberOfItems(): Int {
        return this.employeeItems.size
    }

    fun getEmployee(position: Int): Employee {
        return this.employeeItems[position]
    }
}