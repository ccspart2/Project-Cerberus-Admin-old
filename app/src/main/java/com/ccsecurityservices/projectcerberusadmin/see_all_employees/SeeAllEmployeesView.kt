package com.ccsecurityservices.projectcerberusadmin.see_all_employees

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.add_new_employee.AddNewEmployeeView
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.see_employees_details.SeeEmployeesDetailsView
import kotlinx.android.synthetic.main.see_all_employees.*
import java.io.Serializable

class SeeAllEmployeesView : AppCompatActivity(), SeeAllEmployeesContract.SeeAllEmployeesView {

    private lateinit var seeAllEmployeesPresenter: SeeAllEmployeesPresenter
    private lateinit var adapter: SeeAllEmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_employees)

        seeAllEmployeesPresenter = SeeAllEmployeesPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        see_all_employee_recycler_view.layoutManager = layoutManager

        adapter = SeeAllEmployeesAdapter(seeAllEmployeesPresenter)
        see_all_employee_recycler_view.adapter = adapter

        addEmployeesNAV_BTN.setOnClickListener {
            val navIntent = Intent(this, AddNewEmployeeView::class.java)
            startActivity(navIntent)
        }
        seeAllEmployeesPresenter.getEmployeeList()
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun navToEmployeeDetails(emp: Employee) {
        val navIntent = Intent(this, SeeEmployeesDetailsView::class.java)
        navIntent.putExtra("employee_details", emp as Serializable)
        startActivity(navIntent)
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            see_all_employees_loading_widget.visibility = View.VISIBLE
        } else {
            see_all_employees_loading_widget.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        seeAllEmployeesPresenter.detachListener()
    }
}
