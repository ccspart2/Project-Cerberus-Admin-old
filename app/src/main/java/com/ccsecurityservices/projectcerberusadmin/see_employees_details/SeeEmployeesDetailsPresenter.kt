package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

class SeeEmployeesDetailsPresenter(private val view: SeeEmployeesDetailsContract.SeeEmployeesDetailsView) :
    SeeEmployeesDetailsContract.SeeEmployeesDetailsPresenter {

    private lateinit var currentEmployee : Employee

    override fun retrieveEmployeeObject(EMP: Employee) {
        this.currentEmployee = EMP
        view.populatefields(currentEmployee)
    }

    override fun deleteEmployee(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prepareForEdit(): Employee {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}