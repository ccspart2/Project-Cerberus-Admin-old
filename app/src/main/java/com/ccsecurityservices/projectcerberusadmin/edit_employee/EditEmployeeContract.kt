package com.ccsecurityservices.projectcerberusadmin.edit_employee

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

interface EditEmployeeContract {

    interface EditEmployeeView {
        fun populateView(EMP: Employee)
        fun NavOut()
    }

    interface EditEmployeePresenter {
        fun retreiveExtra(extra: Employee)
        fun UpdateEmployee(
            firstName: String,
            lastName: String,
            email: String,
            phone: String,
            Admin: Boolean)
    }
}