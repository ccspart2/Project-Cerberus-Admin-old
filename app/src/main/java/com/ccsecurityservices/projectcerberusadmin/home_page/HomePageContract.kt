package com.ccsecurityservices.projectcerberusadmin.home_page

import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.firebase.ui.auth.IdpResponse

interface HomePageContract {

    interface HomePageView {
        fun populateUser(): Employee
        fun startLogInFlow()
    }

    interface HomePagePresenter {
        fun retrieveUserInfo()
        fun checkIfLoggedIn()
        fun checkIfValidAdmin(response: IdpResponse)
    }
}