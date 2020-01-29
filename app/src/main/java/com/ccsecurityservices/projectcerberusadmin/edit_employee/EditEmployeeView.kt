package com.ccsecurityservices.projectcerberusadmin.edit_employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    override fun populateView(EMP: Employee) {
        edit_employee_first_name_Edit_Text.setText(EMP.firstName)
        edit_employee_last_name_Edit_Text.setText(EMP.lastName)
        edit_employee_email_Edit_Text.setText((EMP.email))
        edit_employee_phone_Edit_Text.setText(EMP.phone)
        edit_employee_administrator_switch.isChecked = EMP.adminRights

    }

    override fun navOut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
