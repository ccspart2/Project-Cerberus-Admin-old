package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.google.firebase.database.*

class SeeAllEmployeesPresenter(private val view: SeeAllEmployeesView) :
    SeeAllEmployeesContract.SeeAllEmployeesPresenter {

    private val items: MutableList<Employee> = mutableListOf()

    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var employeesReference: DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener

    fun numberOfItems(): Int {
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

        employeesReference = mFireBaseDatabase.reference.child("employees")

        mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                p0.message
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

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
        employeesReference.addChildEventListener(mChildEventListener)
    }

    override fun detachListener() {
        employeesReference.removeEventListener(mChildEventListener)
    }
}