package com.ccsecurityservices.projectcerberusadmin.add_new_employee

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.helper_classes.InputValidation
import com.google.firebase.database.FirebaseDatabase

class AddNewEmployeePresenter(private val view: AddNewEmployeeView) :
    AddNewEmployeeContract.AddNewEmployeePresenter {

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var phone: String

    override fun addEmployee(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        Admin: Boolean
    ) {
        this.firstName = firstName.trim()
        this.lastName = lastName.trim()
        this.email = email.trim()
        this.phone = phone.trim()

        if (InputValidation.inputValidation(
                this.firstName,
                this.lastName,
                this.phone,
                this.email
            )
        ) {
            val employee = Employee(
                "",
                this.firstName,
                this.lastName,
                this.email,
                Admin,
                InputValidation.formatPhone(this.phone),
                "",
                0,
                false
            )

            uploadEmployeeToFireBase(employee)

        } else {
            view.showFailMessage()
        }
    }

    private fun uploadEmployeeToFireBase(employee: Employee) {
        val db = FirebaseDatabase.getInstance().reference
        val id = db.push().key
        employee.id = id!!
        db.child("employees").child(id).setValue(employee).addOnCompleteListener(view) {
            view.navBackSeeAllEmployees()
        }
    }
}
