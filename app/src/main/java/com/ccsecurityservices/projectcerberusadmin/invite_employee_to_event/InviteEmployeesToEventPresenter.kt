package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InviteEmployeesToEventPresenter(private val view: InviteEmployeesToEventView) :
    InviteEmployeesToEventContract.InviteEmployeesToEventPresenter {

    private val mFireBaseDatabase = FirebaseDatabase.getInstance()
    private val employeesReference = mFireBaseDatabase.reference.child("employees")

    private lateinit var currentEvent: Event

    private val invitedEmployeeList: MutableList<Employee> = mutableListOf()
    private val allEmployees: MutableList<Employee> = mutableListOf()

    fun numberOfItems(): Int {
        return allEmployees.size
    }

    fun getEmployee(position: Int): Employee? {
        return allEmployees[position]
    }

    override fun getEmployeesFromFireBase() {
        view.showLoading(true)
        val employeeListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    val emp = dataSnapshot1.getValue(Employee::class.java)!!
                    if (!emp.adminRights) {
                        allEmployees.add(emp)
                    }
                }
                view.showLoading(false)
                view.updateEmployeeList()
            }
        }
        employeesReference.addListenerForSingleValueEvent(employeeListener)
    }

    override fun retrieveEventObjectFromIntent(intent: Intent) {
        this.currentEvent = intent.extras!!.get("initial_event") as Event
        view.populateName(currentEvent.name)
        view.populateFinishBTN(this.invitedEmployeeList.size, this.currentEvent.headcount)
    }

    override fun checkCapacityMet() {
        if (this.invitedEmployeeList.size == this.currentEvent.headcount) {
            createAttendanceList()
        } else {
            view.popUpMessage(
                "Capacity not Reached!",
                "Please keep inviting employees until headcount is met."
            )
        }
    }

    private fun createAttendanceList() {
        view.returnToAddEvent(this.invitedEmployeeList)
    }

    fun addEmployeeToInvitationList(currentEmployee: Employee): Boolean {
        return if (invitedEmployeeList.size < this.currentEvent.headcount) {
            invitedEmployeeList.add(currentEmployee)
            view.populateFinishBTN(this.invitedEmployeeList.size, this.currentEvent.headcount)

            if (invitedEmployeeList.size == this.currentEvent.headcount) {
                view.changeBTNColor(true)
            }
            true
        } else {
            view.popUpMessage(
                "Maximum Capacity Achieved!",
                "You have reached the maximum invitation capacity for this event. If more resources are needed please update the Headcount field on the previous view and try again."
            )
            false
        }
    }

    fun removeEmployeeFromInvitationList(currentEmployee: Employee) {
        invitedEmployeeList.remove(currentEmployee)
        view.populateFinishBTN(this.invitedEmployeeList.size, this.currentEvent.headcount)
        view.changeBTNColor(false)
    }
}