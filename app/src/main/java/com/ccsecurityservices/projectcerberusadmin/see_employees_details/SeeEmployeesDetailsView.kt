package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.ccsecurityservices.projectcerberusadmin.R

class SeeEmployeesDetailsView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_employees_details_layout)

        val currentEmployee = intent.extras!!.get("employee_details") as Employees

        Toast.makeText(this,"Aqui hablaremos de ${currentEmployee.firstName}",Toast.LENGTH_LONG).show()
    }
}
