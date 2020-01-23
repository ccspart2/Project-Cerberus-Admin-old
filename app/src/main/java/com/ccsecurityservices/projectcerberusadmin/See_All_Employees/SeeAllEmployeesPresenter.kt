package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import com.ccsecurityservices.projectcerberusadmin.Data_Items.DummyEmployees
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.ccsecurityservices.projectcerberusadmin.home_page.HomePageContract

class SeeAllEmployeesPresenter(private val view: SeeAllEmployeesContract.SeeAllEmployeesView) :
    HomePageContract.HomePagePresenter {

    private val items: MutableList<Employees> = DummyEmployees.dEmployees.toMutableList()

    override fun retrieveUserInfo() {
        TODO("not implemented") //Conectar Aqui a Firebase DB para bajar los Employees
    }

    fun numberOfItems(): Int {
        return items.size
    }

    fun getEmployee(position: Int): Employees? {
        return items[position]
    }
}