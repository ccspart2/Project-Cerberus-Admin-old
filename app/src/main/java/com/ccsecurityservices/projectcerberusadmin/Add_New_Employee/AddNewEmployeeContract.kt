package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

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