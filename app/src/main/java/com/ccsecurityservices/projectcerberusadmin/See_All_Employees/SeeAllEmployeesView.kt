package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.Add_New_Employee.AddNewEmployeeView
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_employees_details.SeeEmployeesDetailsView
import kotlinx.android.synthetic.main.see_all_employees.*
import java.io.Serializable

class SeeAllEmployeesView : AppCompatActivity(), SeeAllEmployeesContract.SeeAllEmployeesView {

    private lateinit var seeAllEmployeesPresenter: SeeAllEmployeesPresenter
    private lateinit var adapter : SeeAllEmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_employees)

        seeAllEmployeesPresenter = SeeAllEmployeesPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        allEmployeesRV.layoutManager = layoutManager

        adapter = SeeAllEmployeesAdapter(seeAllEmployeesPresenter)
        allEmployeesRV.adapter = adapter

        addEmployeesNAV_BTN.setOnClickListener {
            val navIntent = Intent(this, AddNewEmployeeView::class.java)
            startActivity(navIntent)
        }
         seeAllEmployeesPresenter.getEmployeeList()
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun navToEmployeeDetails(emp: Employees) {
        val navIntent = Intent(this, SeeEmployeesDetailsView::class.java)
        navIntent.putExtra("employee_details", emp as Serializable)
        startActivity(navIntent)
    }
}
