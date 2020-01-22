package com.ccsecurityservices.projectcerberusadmin.home_page

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ccsecurityservices.projectcerberusadmin.Data_Items.User
import com.ccsecurityservices.projectcerberusadmin.R

class HomePage : AppCompatActivity(), HomePageContract.HomePageView {

    private lateinit var employeesBTN: ImageView

    private lateinit var homePagePresenter: HomePagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        employeesBTN = findViewById(R.id.employees_btn)

        employeesBTN.setOnClickListener{
            Toast.makeText(this, "Funciona", Toast.LENGTH_LONG).show()
        }




    }

    override fun populateUser(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun NavEmployees() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun NavEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun NavLocations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun LogOut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
