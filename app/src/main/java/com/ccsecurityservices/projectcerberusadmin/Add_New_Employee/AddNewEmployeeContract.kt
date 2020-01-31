package com.ccsecurityservices.projectcerberusadmin.add_new_employee

interface AddNewEmployeeContract {

    interface AddNewEmployeeView {
        fun navBacktoSeeAllEmployees()
        fun showFailMessage()
    }

    interface AddNewEmployeePresenter {
        fun addEmployee(
            firstName: String, lastName: String,
            email: String, phone: String, Admin: Boolean
        )
    }
}