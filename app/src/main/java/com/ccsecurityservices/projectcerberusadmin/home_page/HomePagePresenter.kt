package com.ccsecurityservices.projectcerberusadmin.home_page

import com.google.firebase.auth.FirebaseAuth

class HomePagePresenter(private val view: HomePageContract.HomePageView) :
    HomePageContract.HomePagePresenter {
    private lateinit var auth: FirebaseAuth

    override fun retrieveUserInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logOut() {
        view.navToSignIn()
    }
}