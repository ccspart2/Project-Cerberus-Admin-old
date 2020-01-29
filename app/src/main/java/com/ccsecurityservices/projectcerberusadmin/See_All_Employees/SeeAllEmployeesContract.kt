package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

interface SeeAllEmployeesContract {

    interface SeeAllEmployeesView {
        fun updateList()
        fun navToEmployeeDetails(emp : Employee)
        fun showLoading(state : Boolean)
    }
    interface SeeAllEmployeesPresenter {
        fun getEmployeeList()
        fun detachListener()
    }
}