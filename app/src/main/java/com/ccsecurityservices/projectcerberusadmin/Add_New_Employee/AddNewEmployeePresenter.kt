package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

import android.telephony.PhoneNumberUtils
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.ccsecurityservices.projectcerberusadmin.helper_classes.EmployeeInputValidation
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

        if (EmployeeInputValidation.inputValidation(
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
                EmployeeInputValidation.formatPhone(this.phone),
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
            view.navBacktoSeeAllEmployees()
        }
    }
}
