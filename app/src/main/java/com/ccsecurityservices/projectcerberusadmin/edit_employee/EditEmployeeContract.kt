package com.ccsecurityservices.projectcerberusadmin.edit_employee

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

interface EditEmployeeContract {

    interface EditEmployeeView {
        fun populateView(EMP: Employee)
        fun navOut()
    }

    interface EditEmployeePresenter {
        fun retrieveExtra(extra: Employee)
        fun updateEmployee(
            firstName: String,
            lastName: String,
            email: String,
            phone: String,
            Admin: Boolean)
    }
}