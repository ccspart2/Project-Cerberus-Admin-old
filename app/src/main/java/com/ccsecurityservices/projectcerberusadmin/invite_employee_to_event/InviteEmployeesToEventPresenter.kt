package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InviteEmployeesToEventPresenter(private val view: InviteEmployeesToEventView) :
    InviteEmployeesToEventContract.InviteEmployeesToEventPresenter {

    private val mFireBaseDatabase = FirebaseDatabase.getInstance()
    private val employeesReference = mFireBaseDatabase.reference.child("employees")

    private val employeeItems: MutableList<Employee> = mutableListOf()

    fun numberOfItems(): Int {
        return employeeItems.size
    }

    fun getEmployee(position: Int): Employee? {
        return employeeItems[position]
    }

    override fun getEmployeesFromFireBase() {

        val employeeListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    val emp = dataSnapshot1.getValue(Employee::class.java)!!
                    if (!emp.adminRights) {
                        employeeItems.add(emp)
                    }
                }
                view.updateEmployeeList()
            }
        }
        employeesReference.addListenerForSingleValueEvent(employeeListener)
    }
}