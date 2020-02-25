package com.ccsecurityservices.projectcerberusadmin.sign_in

import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseUser

interface SignInContract {
    interface SignInView {
        fun navToHomePage()
        fun startSignInFlow()
        fun invalidUserDialog()
        fun displayLoading(state: Boolean)
    }

    interface SignInPresenter {
        fun checkIfSignedIn()
        fun checkIfAdmin(currentUser: FirebaseUser)
    }
}