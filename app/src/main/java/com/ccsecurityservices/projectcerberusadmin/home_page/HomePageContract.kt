package com.ccsecurityservices.projectcerberusadmin.home_page

import com.ccsecurityservices.projectcerberusadmin.Data_Items.User

interface HomePageContract {

    interface HomePageView {
        fun populateUser(): User
    }

    interface HomePagePresenter {
        fun retrieveUserInfo()
    }
}