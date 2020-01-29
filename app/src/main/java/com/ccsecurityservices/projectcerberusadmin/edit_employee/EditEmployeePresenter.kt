package com.ccsecurityservices.projectcerberusadmin.edit_employee

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

class EditEmployeePresenter (private val view : EditEmployeeView) : EditEmployeeContract.EditEmployeePresenter
{
    override fun retreiveExtra(extra: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun UpdateEmployee(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        Admin: Boolean
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}