package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.Data_Items.User
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.See_All_Employees.SeeAllEmployees

class HomePage : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var employeesBTN: ImageView

    private lateinit var homePagePresenter: HomePagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        homePagePresenter = HomePagePresenter(this)
        employeesBTN = findViewById(R.id.employees_btn)

        employeesBTN.setOnClickListener {
            navigateOutHomePage(it.id)
        }
    }

    private fun navigateOutHomePage(viewID: Int) {
        when (viewID) {
            R.id.employees_btn -> {
                val navIntent = Intent(this, SeeAllEmployees::class.java)
                startActivity(navIntent)
            }
            R.id.events_btn -> {
                TODO("not implemented")
            }
            R.id.locations_btn -> {
                TODO("not implemented")
            }
        }
    }

    override fun populateUser(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
