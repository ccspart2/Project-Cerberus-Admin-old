package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_employees_details.*

class SeeEmployeesDetailsView : AppCompatActivity(),
    SeeEmployeesDetailsContract.SeeEmployeesDetailsView {

    private lateinit var presenter: SeeEmployeesDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_employees_details)

        presenter = SeeEmployeesDetailsPresenter(this)
        presenter.retrieveEmployeeObject(intent.extras!!.get("employee_details") as Employee)
    }

    override fun populatefields(EMP: Employee) {
        see_employee_details_name_text_view.text =
            getString(R.string.see_employee_details_name_text_placeholder).plus(" ${EMP.firstName} ${EMP.lastName}")
        see_employee_details_phone_text_view.text =
            getString(R.string.see_employee_details_phone_text_placeholder).plus(" ${EMP.phone}")
        see_employee_details_email_text_view.text =
            getString(R.string.see_employee_details_email_text_placeholder).plus(" ${EMP.email}")

        if (EMP.adminRights) {
            see_employee_details_admin_text_view.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Administrator")
        } else {
            see_employee_details_admin_text_view.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Employee")
        }

        if (EMP.hasApp) {
            see_employee_details_hasApp_text_view.text =
                getString(R.string.see_employee_details_hasApp_text_placeholder).plus(" YES")
        } else {
            see_employee_details_hasApp_text_view.text =
                getString(R.string.see_employee_details_hasApp_text_placeholder).plus(" NO")
        }
    }

    override fun deleteResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
