package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.Data_Items.DummyEmployees
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_all_employees.*

class SeeAllEmployees : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_employees)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        allEmployeesRV.layoutManager = layoutManager

        val adapter = SeeAllEmployeesAdapter(this, DummyEmployees.dEmployees)
        allEmployeesRV.adapter = adapter

    }
}
