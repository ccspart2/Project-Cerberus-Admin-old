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
import java.time.LocalDateTime

class SeeEventStatusPresenter(private val view: SeeEventStatusView) :
    SeeEventStatusContract.SeeEventStatusPresenter {

    private lateinit var currentEvent: Event
    private lateinit var attendanceList: MutableList<Attendance>

    private val attendanceChanges =
        mutableMapOf("DELETE" to mutableListOf<Employee>(), "ADD" to mutableListOf())

    private var employeeItems: MutableList<Employee> = mutableListOf()
    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun retrieveData(intent: Intent) {
        val eventId = intent.extras!!.getString("event_status")
        retrieveEventObject(eventId!!)
        retrieveEmployeeList()
    }

    override fun sortByAttendance() {
        val tempList: MutableList<Employee> = mutableListOf()
        val currentHeadcount = this.attendanceList.size
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Accepted") {
                tempList.add(emp)
            }
        }
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Invited") {
                tempList.add(emp)
            }
        }
        employeeItems.forEach { emp ->
            if (employeeAttendanceStatus(emp.id) == "Declined") {
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
        view.populateHeadcount(this.currentEvent.headcount, currentHeadcount)
    }

    override fun updateFireBaseDB() {

        if (checkIfHeadCountIsMet()) {
            view.displayLoading(true)
            removeAttendanceFromEmployee(this.attendanceChanges["DELETE"]!!)
            addAttendanceFromEmployee(this.attendanceChanges["ADD"]!!)
            updateEventOnDB()
        } else {
            view.displayDialog(
                "Headcount not met",
                "Please invite at least the amount of the headcount. You are able to invite more than the headcount but not less."
            )
        }
    }

    private fun checkIfHeadCountIsMet(): Boolean {
        var count =
            this.attendanceChanges["ADD"]!!.size + this.attendanceList.size - this.attendanceChanges["DELETE"]!!.size

        return count >= this.currentEvent.headcount
    }

    private fun addAttendanceFromEmployee(employeeList: MutableList<Employee>) {
        val employeeReference = mFireBaseDatabase.reference
        var tempAttendance: Attendance

        if (employeeList.size > 0) {
            employeeList.forEach { emp ->
                tempAttendance = Attendance(
                    employeeReference.push().key!!
                    , emp.id
                    , "Invited"
                    , LocalDateTime.now().toString()
                )
                this.attendanceList.add(tempAttendance)
                employeeReference.child("employees/${emp.id}/attendanceList/${tempAttendance.id}")
                    .setValue(tempAttendance)
            }
        }
    }

    private fun updateEventOnDB() {

        val tempMap: MutableMap<String, Attendance> = mutableMapOf()
        val eventReference = mFireBaseDatabase.reference

        this.attendanceList.forEach {
            tempMap[it.id] = it
        }
        this.currentEvent.attendanceList = tempMap

        eventReference.child("events/active/${this.currentEvent.id}").setValue(this.currentEvent)
        view.updateList()
        this.attendanceChanges["DELETE"] = mutableListOf()
        this.attendanceChanges["ADD"] = mutableListOf()
        view.displayLoading(false)
        view.navBack()
    }

    private fun removeAttendanceFromEmployee(employeeList: MutableList<Employee>) {

        val attendanceReference = mFireBaseDatabase.reference

        if (employeeList.size > 0) {
            employeeList.forEach { employee ->

                attendanceReference
                    .child("employees")
                    .child(employee.id)
                    .child("attendanceList")
                    .child(this.attendanceList.find {
                        it.employeeId == employee.id
                    }!!.id)
                    .removeValue()

                this.attendanceList.removeIf {
                    it.employeeId == employee.id
                }
            }
        }
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
                attendanceList = currentEvent.attendanceList.values.toMutableList()
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
                view.displayLoading(false)

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

    fun addToDeleteMap(emp: Employee) {
        val tempList = this.attendanceChanges.getValue("DELETE")
        if (!tempList.contains(emp)) {
            tempList.add(emp)
            this.attendanceChanges["DELETE"] = tempList
        }
    }

    fun addToAddMap(emp: Employee) {
        val tempList = this.attendanceChanges.getValue("ADD")
        if (!tempList.contains(emp)) {
            tempList.add(emp)
            this.attendanceChanges["ADD"] = tempList
        }
    }
}