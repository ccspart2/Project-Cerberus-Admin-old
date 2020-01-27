package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees

interface SeeAllEmployeesContract {

    interface SeeAllEmployeesView {
        fun updateList()
        fun navToEmployeeDetails(emp : Employees)
    }
    interface SeeAllEmployeesPresenter {
        fun getEmployeeList()
    }
}