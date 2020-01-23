package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.Data_Items.User
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.See_All_Employees.SeeAllEmployeesView
import kotlinx.android.synthetic.main.home_page.*

class HomePage : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var homePagePresenter: HomePagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        homePagePresenter = HomePagePresenter(this)

        employees_btn.setOnClickListener {
            navigateOutHomePage(it.id)
        }
    }

    private fun navigateOutHomePage(viewID: Int) {
        when (viewID) {
            R.id.employees_btn -> {
                val navIntent = Intent(this, SeeAllEmployeesView::class.java)
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
