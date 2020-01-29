package com.ccsecurityservices.projectcerberusadmin.edit_employee

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

class EditEmployeePresenter (private val view : EditEmployeeView) : EditEmployeeContract.EditEmployeePresenter
{
    private lateinit var currentEmployee : Employee

    override fun retrieveExtra(extra: Employee) {
        this.currentEmployee = extra
        view.populateView(this.currentEmployee)
    }

    override fun updateEmployee(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        Admin: Boolean
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}