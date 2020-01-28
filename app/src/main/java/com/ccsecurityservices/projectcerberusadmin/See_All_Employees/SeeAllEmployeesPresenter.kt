package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.google.firebase.database.*

class SeeAllEmployeesPresenter(private val view: SeeAllEmployeesContract.SeeAllEmployeesView) :
    SeeAllEmployeesContract.SeeAllEmployeesPresenter {

    private val items: MutableList<Employees> = mutableListOf()

    private lateinit var mFireBaseDatabase : FirebaseDatabase
    private lateinit var employeesReference : DatabaseReference
    private lateinit var mChildEventListener: ChildEventListener


    fun numberOfItems(): Int {
        return items.size
    }

    fun getEmployee(position: Int): Employees? {
        return items[position]
    }

    override fun getEmployeeList() {

        mFireBaseDatabase = FirebaseDatabase.getInstance()
        employeesReference = mFireBaseDatabase.reference.child("employees")

        mChildEventListener = object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                p0.message
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(data: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                val emp = data.getValue(Employees::class.java)
                items.add(emp!!)
                sortEmps()
                view.updateList()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        employeesReference.addChildEventListener(mChildEventListener)
    }

    private fun sortEmps() {
        items.sortBy { it.firstName + it.lastName }
    }

    fun setupNavToEmployeeDetails(currentEmployee: Employees?) {
        view.navToEmployeeDetails(currentEmployee!!)
    }
}