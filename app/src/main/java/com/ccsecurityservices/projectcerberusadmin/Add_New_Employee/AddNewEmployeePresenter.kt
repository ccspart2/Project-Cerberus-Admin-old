package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNewEmployeePresenter(private val view: AddNewEmployeeContract.AddNewEmployeeView) :
    AddNewEmployeeContract.AddNewEmployeePresenter {
    private lateinit var database: DatabaseReference

    override fun addEmployee(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        Admin: Boolean
    ) {
        //TODO Implementar primero el validation de los fields provistos luego hacer la coneccion con la base de datos
    }

}