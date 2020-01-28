package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

interface SeeEmployeesDetailsContract {

    interface SeeEmployeesDetailsView {
        fun populatefields(EMP: Employee)
        fun deleteResult(result : Boolean)
    }

    interface SeeEmployeesDetailsPresenter {
        fun retrieveEmployeeObject(EMP: Employee)
        fun deleteEmployee(): Boolean
        fun prepareForEdit(): Employee
    }
}