package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeEventStatusPresenter(private val view: SeeEventStatusView) :
    SeeEventStatusContract.SeeEventStatusPresenter {

    private lateinit var currentEvent: Event
    private lateinit var attendanceList: List<Attendance>
    private var employeeItems: MutableList<Employee> = mutableListOf()
    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun retrieveData(intent: Intent) {
        val eventId = intent.extras!!.getString("event_status")
        retrieveEventObject(eventId!!)
        retrieveEmployeeList()
    }

    override fun sortByAttendance() {
        val tempList: MutableList<Employee> = mutableListOf()

        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Accepted") {
                tempList.add(emp)
            }
        }
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Declined") {
                tempList.add(emp)
            }
        }
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Invited") {
                tempList.add(emp)
            }
        }
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "not invited") {
                tempList.add(emp)
            }
        }
        this.employeeItems.clear()
        this.employeeItems = tempList
    }

    private fun retrieveEventObject(eventID: String) {

        val eventReference = mFireBaseDatabase.reference
            .child("events/active/$eventID")

        val eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("SeeEventStatus", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                currentEvent = p0.getValue(Event::class.java)!!
                attendanceList = currentEvent.attendanceList.values.toList()
                view.populateActivityLabel(currentEvent.name)
                if (employeeItems.size > 0) {
                    view.updateList()
                }
            }
        }
        eventReference.addValueEventListener(eventListener)
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
                    val emp = it.getValue(Employee::class.java)!!
                    if (!emp.adminRights) {
                        employeeItems.add(emp)
                    }
                }
                view.updateList()
            }
        }
        employeesReference.addListenerForSingleValueEvent(employeesListener)
    }

    fun employeeAttendanceStatus(empID: String): String {
        val attendance = this.attendanceList.find {
            it.employeeId == empID
        }
        return attendance?.status ?: "not invited"
    }

    fun numberOfItems(): Int {
        return this.employeeItems.size
    }

    fun getEmployee(position: Int): Employee {
        return this.employeeItems[position]
    }
}