package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

interface SeeAllEmployeesContract {

    interface SeeAllEmployeesView {
        fun updateList()
    }
    interface SeeAllEmployeesPresenter {
        fun getEmployeeList()
    }
}