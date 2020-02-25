package com.ccsecurityservices.projectcerberusadmin.home_page

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee

interface HomePageContract {

    interface HomePageView {
        fun populateUser(): Employee
        fun navToSignIn()
    }

    interface HomePagePresenter {
        fun retrieveUserInfo()
        fun logOut()
    }
}