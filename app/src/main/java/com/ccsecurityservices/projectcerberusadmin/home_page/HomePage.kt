package com.ccsecurityservices.projectcerberusadmin.home_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccsecurityservices.projectcerberusadmin.Data_Items.User
import com.ccsecurityservices.projectcerberusadmin.R

class HomePage : AppCompatActivity(), HomePageContract.HomePageView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
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
