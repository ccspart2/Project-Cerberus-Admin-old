package com.ccsecurityservices.projectcerberusadmin.see_all_employees

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee

interface SeeAllEmployeesContract {

    interface SeeAllEmployeesView {
        fun updateList()
        fun navToEmployeeDetails(emp: Employee)
        fun showLoading(state: Boolean)
    }

    interface SeeAllEmployeesPresenter {
        fun getEmployeeList()
        fun detachListener()
    }
}