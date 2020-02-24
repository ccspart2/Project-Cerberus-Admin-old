package com.ccsecurityservices.projectcerberusadmin.add_new_employee

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.add_new_employee.*

class AddNewEmployeeView : AppCompatActivity(), AddNewEmployeeContract.AddNewEmployeeView {

    private lateinit var addNewEmployeePresenter: AddNewEmployeePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_employee)

        addNewEmployeePresenter = AddNewEmployeePresenter(this)

        add_employee_BTN.setOnClickListener {
            showDialogMessage(
                "Create Employee",
                "Are you sure you want to create this employee?",
                "CREATE_EMPLOYEE"
            )
        }
    }

    override fun navBackSeeAllEmployees() {
        Toast.makeText(
            this,
            "The employee was successfully created.",
            Toast.LENGTH_LONG
        ).show()
        finish()
    }

    override fun showDialogMessage(title: String, desc: String, action: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(desc)
        if (action == "INVALID_CREDENTIALS") {
            builder.setNegativeButton("Ok") { _, _ -> }
        } else if (action == "CREATE_EMPLOYEE") {
            builder.setPositiveButton("Ok") { _, _ ->
                addNewEmployeePresenter.addEmployee(
                    add_employee_first_name_Edit_Text.text.toString(),
                    add_employee_last_name_Edit_Text.text.toString(),
                    add_employee_email_Edit_Text.text.toString(),
                    add_employee_phone_Edit_Text.text.toString(),
                    add_employee_administrator_switch.isChecked
                )
            }
        }
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        add_employee_first_name_Edit_Text.setText("")
        add_employee_last_name_Edit_Text.setText("")
        add_employee_email_Edit_Text.setText("")
        add_employee_phone_Edit_Text.setText("")
        add_employee_administrator_switch.isChecked = false
    }
}
