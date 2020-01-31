package com.ccsecurityservices.projectcerberusadmin.home_page

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee

interface HomePageContract {

    interface HomePageView {
        fun populateUser(): Employee
    }

    interface HomePagePresenter {
        fun retrieveUserInfo()
    }
}