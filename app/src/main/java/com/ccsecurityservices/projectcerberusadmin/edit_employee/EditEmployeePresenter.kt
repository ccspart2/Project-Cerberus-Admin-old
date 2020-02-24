package com.ccsecurityservices.projectcerberusadmin.edit_employee

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.helper_classes.InputValidation
import com.google.firebase.database.FirebaseDatabase

class EditEmployeePresenter(private val view: EditEmployeeView) :
    EditEmployeeContract.EditEmployeePresenter {

    private lateinit var currentEmployee: Employee
    private val mFireBaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var phone: String

    override fun retrieveExtra(extra: Employee) {
        this.currentEmployee = extra
        view.populateView(this.currentEmployee)
    }

    override fun updateEmployee(
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
                this.currentEmployee.id,
                this.firstName,
                this.lastName,
                this.email,
                Admin,
                InputValidation.formatPhone(this.phone),
                this.currentEmployee.photoId,
                false,
                this.currentEmployee.attendanceList
            )
            uploadEmployeeToFireBase(employee)
        } else {
            view.displayInvalidParameters()
        }
    }

    private fun uploadEmployeeToFireBase(employee: Employee) {
        val dbReference = mFireBaseDatabase.reference
            .child("employees/regulars/${this.currentEmployee.id}")

        dbReference.setValue(employee).addOnCompleteListener(view) {
            view.navOut()
        }
    }
}