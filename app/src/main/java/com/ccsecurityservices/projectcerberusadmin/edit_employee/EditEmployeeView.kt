package com.ccsecurityservices.projectcerberusadmin.edit_employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.edit_employee.*

class EditEmployeeView : AppCompatActivity(), EditEmployeeContract.EditEmployeeView {

    private lateinit var presenter: EditEmployeePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_employee)

        presenter = EditEmployeePresenter(this)
        presenter.retrieveExtra(intent.extras!!.get("employee_edit") as Employee)

        edit_employee_BTN.setOnClickListener {
            presenter.updateEmployee(
                edit_employee_first_name_Edit_Text.text.toString(),
                edit_employee_last_name_Edit_Text.text.toString(),
                edit_employee_email_Edit_Text.text.toString(),
                edit_employee_phone_Edit_Text.text.toString(),
                edit_employee_administrator_switch.isChecked
            )
        }
    }

    override fun populateView(EMP: Employee) {
        edit_employee_first_name_Edit_Text.setText(EMP.firstName)
        edit_employee_last_name_Edit_Text.setText(EMP.lastName)
        edit_employee_email_Edit_Text.setText((EMP.email))
        edit_employee_phone_Edit_Text.setText(EMP.phone)
        edit_employee_administrator_switch.isChecked = EMP.adminRights
    }

    override fun navOut() {
        Toast.makeText(this, "The Employee was successfully updated", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun displayInvalidParameters() {
        Toast.makeText(
            this, "Some of the credentials are not valid. Please verify and try again",
            Toast.LENGTH_LONG
        ).show()
    }
}
