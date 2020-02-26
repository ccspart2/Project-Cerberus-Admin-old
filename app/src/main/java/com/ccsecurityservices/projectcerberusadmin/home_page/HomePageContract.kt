package com.ccsecurityservices.projectcerberusadmin.home_page

interface HomePageContract {

    interface HomePageView {
        fun populateUser(name : String, photoURL: String)
        fun navToSignIn()
    }

    interface HomePagePresenter {
        fun retrieveUserInfo()
        fun logOut()
    }
}