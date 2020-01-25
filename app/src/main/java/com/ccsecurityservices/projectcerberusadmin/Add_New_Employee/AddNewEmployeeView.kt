package com.ccsecurityservices.projectcerberusadmin.Add_New_Employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.add_new_employee.*
import kotlinx.android.synthetic.main.see_all_employees.*

class AddNewEmployeeView : AppCompatActivity(), AddNewEmployeeContract.AddNewEmployeeView {

    private lateinit var addNewEmployeePresenter: AddNewEmployeePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_employee)

        addNewEmployeePresenter = AddNewEmployeePresenter(this)

        addEmployeesBTN.setOnClickListener {
            addNewEmployeePresenter.addEmployee(
                add_employee_first_name_Edit_Text.text.toString(),
                add_employee_last_name_Edit_Text.text.toString(),
                add_employee_email_Edit_Text.toString(),
                add_employee_phone_Edit_Text.toString(),
                add_employee_administrator_switch.isChecked
            )
        }
    }

    override fun navBacktoSeeAllEmployees() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFailMessage() {
        Toast.makeText(
            this,
            "Some of the credentials are not valid. Please verify and try again",
            Toast.LENGTH_LONG
        ).show()
    }
}
