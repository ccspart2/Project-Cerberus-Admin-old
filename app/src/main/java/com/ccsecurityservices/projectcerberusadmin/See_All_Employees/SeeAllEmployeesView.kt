package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.Add_New_Employee.AddNewEmployeeView
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_all_employees.*

class SeeAllEmployeesView : AppCompatActivity(), SeeAllEmployeesContract.SeeAllEmployeesView {

    private lateinit var seeAllEmployeesPresenter: SeeAllEmployeesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_employees)

        seeAllEmployeesPresenter = SeeAllEmployeesPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        allEmployeesRV.layoutManager = layoutManager

        val adapter = SeeAllEmployeesAdapter(seeAllEmployeesPresenter)
        allEmployeesRV.adapter = adapter

        //Dummy Add Employees for Cloud Testing
//        addEmployeesBTN.setOnClickListener{
//            Toast.makeText(this,"Me tocaste!",Toast.LENGTH_LONG).show()
//            seeAllEmployeesPresenter.dummyAddCloudEmployee()
//
//        }

        addEmployeesNAV_BTN.setOnClickListener {
            val navIntent = Intent(this, AddNewEmployeeView::class.java)
            startActivity(navIntent)
        }
    }

    override fun updateList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
