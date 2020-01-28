package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

import android.telephony.PhoneNumberUtils
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class AddNewEmployeePresenter(private val view: AddNewEmployeeContract.AddNewEmployeeView) :
    AddNewEmployeeContract.AddNewEmployeePresenter {

    companion object {

        const val PHONE_REGEX =
            "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"

        const val FIRST_NAME_REGEX = "[A-Z][a-zA-Z]*"

        const val LAST_NAME_REGEX = "[a-zA-z]+([ '-][a-zA-Z]+)*"

        const val EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }

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

        if (validateFields()) {
            val employee = Employee(
                "",
                this.firstName,
                this.lastName,
                this.email,
                Admin,
                formatPhone(),
                "",
                0,
                false
            )

            uploadEmployeeToFireBase(employee)
            view.navBacktoSeeAllEmployees()

        } else {
            view.showFailMessage()
        }
    }

    private fun formatPhone(): String {
        return if (!this.phone.contains('(')) {
            PhoneNumberUtils.formatNumber(this.phone)
        } else {
            this.phone
        }
    }

    private fun uploadEmployeeToFireBase(employee: Employee) {
        val db = FirebaseDatabase.getInstance().reference
        val id = db.push().key
        employee.id = id!!
        db.child("employees").child(id).setValue(employee)
    }

    private fun validateFields(): Boolean {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return false
        }

        if (!isFirstNameValid() || !isLastNameValid() || !isEmailValid() || !isPhoneNumberValid()) {
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        return this.email.matches(EMAIL_REGEX.toRegex())
    }

    private fun isLastNameValid(): Boolean {
        return this.lastName.matches(LAST_NAME_REGEX.toRegex())
    }

    private fun isFirstNameValid(): Boolean {
        return this.firstName.matches(FIRST_NAME_REGEX.toRegex())
    }

    private fun isPhoneNumberValid(): Boolean {
        val pattern = Pattern.compile(PHONE_REGEX)
        val matcher = pattern.matcher(this.phone.trim())
        return matcher.find()
    }
}
