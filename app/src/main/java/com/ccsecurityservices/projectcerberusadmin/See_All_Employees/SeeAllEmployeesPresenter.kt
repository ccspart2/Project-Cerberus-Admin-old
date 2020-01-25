package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.DummyEmployees
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.google.firebase.database.FirebaseDatabase

class SeeAllEmployeesPresenter(private val view: SeeAllEmployeesContract.SeeAllEmployeesView) :
    SeeAllEmployeesContract.SeeAllEmployeesPresenter {

    private val items: MutableList<Employees> = DummyEmployees.dEmployees.toMutableList()

    private val mFireBaseDatabase = FirebaseDatabase.getInstance()
    private val mDatabaseReference = mFireBaseDatabase.reference.child("employees")

    fun numberOfItems(): Int {
        return items.size
    }

    fun getEmployee(position: Int): Employees? {
        return items[position]
    }

    override fun getEmployeeList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun dummyAddCloudEmployee() {

        val dummyEMP = Employees(
            "1", "Charlie1", "Castro",
            "ccspart2@gmail.com", true, "787-509-1818", null,
            null, false
        )
        mDatabaseReference.push().setValue(dummyEMP)
    }
}