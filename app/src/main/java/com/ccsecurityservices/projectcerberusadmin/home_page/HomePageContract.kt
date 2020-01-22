package com.ccsecurityservices.projectcerberusadmin.home_page

import com.ccsecurityservices.projectcerberusadmin.Data_Items.User

interface HomePageContract {

    interface HomePageView {
        fun populateUser(): User
        fun NavEmployees()
        fun NavEvents()
        fun NavLocations()
        fun LogOut()
    }

    interface HomePagePresenter {
        fun retreiveUserInfo()
    }
}