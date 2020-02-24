package com.ccsecurityservices.projectcerberusadmin.see_all_employees

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.database.*

class SeeAllEmployeesPresenter(private val view: SeeAllEmployeesView) :
    SeeAllEmployeesContract.SeeAllEmployeesPresenter {

    private val items: MutableList<Employee> = mutableListOf()

    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var employeesRegularsReference: DatabaseReference
    private lateinit var employeeAdminReference: DatabaseReference
    private lateinit var regularEmployeesListener: ChildEventListener

    fun numberOfItems(): Int {
        if (items.size == 0) {
            view.showLoading(false)
        }
        return items.size
    }

    fun getEmployee(position: Int): Employee? {
        return items[position]
    }

    private fun sortItems() {
        items.sortBy { it.firstName + it.lastName }
    }

    fun setupNavToEmployeeDetails(currentEmployee: Employee?) {
        view.navToEmployeeDetails(currentEmployee!!)
    }

    override fun getEmployeeList() {

        employeesRegularsReference = mFireBaseDatabase.reference.child("employees/regulars")
        employeeAdminReference = mFireBaseDatabase.reference.child("employees/admins")

        regularEmployeesListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                p0.message
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(data: DataSnapshot, p1: String?) {
                val changedEMP = data.getValue(Employee::class.java)
                val oldEMP = items.find { it.id == changedEMP!!.id }
                items.remove(oldEMP)
                items.add(changedEMP!!)
                sortItems()
                view.updateList()
                view.showLoading(false)
            }

            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                val emp = data.getValue(Employee::class.java)
                items.add(emp!!)
                sortItems()
                view.updateList()
                view.showLoading(false)
            }

            override fun onChildRemoved(data: DataSnapshot) {
                val deletedId = data.getValue(Employee::class.java)!!.id
                val item = items.find { it.id == deletedId }
                items.remove(item)
                view.updateList()
                view.showLoading(false)
            }
        }
        employeesRegularsReference.addChildEventListener(regularEmployeesListener)
        employeeAdminReference.addChildEventListener(regularEmployeesListener)
    }

    override fun detachListener() {
        employeesRegularsReference.removeEventListener(regularEmployeesListener)
    }
}