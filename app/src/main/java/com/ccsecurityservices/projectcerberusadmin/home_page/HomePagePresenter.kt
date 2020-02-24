package com.ccsecurityservices.projectcerberusadmin.home_page

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class HomePagePresenter(private val view: HomePageContract.HomePageView) :
    HomePageContract.HomePagePresenter {

    override fun retrieveUserInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkIfLoggedIn() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {

        } else {
            view.startLogInFlow()
        }
    }

    override fun checkIfValidAdmin(response: IdpResponse) {

        val auth = response.email

    }
}