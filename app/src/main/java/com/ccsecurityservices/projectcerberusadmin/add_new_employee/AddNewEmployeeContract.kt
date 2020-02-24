package com.ccsecurityservices.projectcerberusadmin.add_new_employee

interface AddNewEmployeeContract {

    interface AddNewEmployeeView {
        fun navBackSeeAllEmployees()
        fun showDialogMessage(title: String, desc: String, action: String)
    }

    interface AddNewEmployeePresenter {
        fun addEmployee(
            firstName: String, lastName: String,
            email: String, phone: String, Admin: Boolean
        )
    }
}