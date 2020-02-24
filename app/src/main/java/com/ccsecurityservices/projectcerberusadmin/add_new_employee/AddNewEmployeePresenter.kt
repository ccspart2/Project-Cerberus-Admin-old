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
                false,
                mutableMapOf()
            )
            uploadEmployeeToFireBase(employee)
        } else {
            view.showDialogMessage(
                "Invalid Credentials",
                "Some of the credentials are not valid. Please verify and try again.",
                "INVALID_CREDENTIALS"
            )
        }
    }

    private fun uploadEmployeeToFireBase(employee: Employee) {
        val db = FirebaseDatabase.getInstance().reference
        val id = db.push().key
        employee.id = id!!

        if (employee.adminRights) {
            db.child("employees/admins").child(id).setValue(employee).addOnCompleteListener(view) {
                view.navBackSeeAllEmployees()
            }
        } else {
            db.child("employees/regulars").child(id).setValue(employee)
                .addOnCompleteListener(view) {
                    view.navBackSeeAllEmployees()
                }
        }
    }
}
