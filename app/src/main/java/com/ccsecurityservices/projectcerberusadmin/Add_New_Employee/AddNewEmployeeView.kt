package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.add_new_employee.*
import kotlinx.android.synthetic.main.see_all_employees.*

class AddNewEmployeeView : AppCompatActivity(), AddNewEmployeeContract.AddNewEmployeeView {

    private lateinit var addEmployeeBTN : Button
    private lateinit var addNewEmployeePresenter: AddNewEmployeePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_employee)

        addNewEmployeePresenter = AddNewEmployeePresenter(this)



        add_employee_BTN.setOnClickListener {
            addNewEmployeePresenter.addEmployee(
                add_employee_first_name_Edit_Text.text.toString(),
                add_employee_last_name_Edit_Text.text.toString(),
                add_employee_email_Edit_Text.text.toString(),
                add_employee_phone_Edit_Text.text.toString(),
                add_employee_administrator_switch.isChecked
            )
        }
    }

    override fun navBacktoSeeAllEmployees() {
        Toast.makeText(
            this,
            "Tus credenciales estan Bien!!!!!",
            Toast.LENGTH_LONG
        ).show()    }

    override fun showFailMessage() {
        Toast.makeText(
            this,
            "Some of the credentials are not valid. Please verify and try again",
            Toast.LENGTH_LONG
        ).show()
    }
}
