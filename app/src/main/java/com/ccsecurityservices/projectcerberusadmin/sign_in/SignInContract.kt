package com.ccsecurityservices.projectcerberusadmin.sign_in

import com.google.firebase.auth.FirebaseUser

interface SignInContract {
    interface SignInView {
        fun navToHomePage()
        fun startSignInFlow()
        fun invalidUserDialog()
    }

    interface SignInPresenter {
        fun checkIfSignedIn()
        fun checkIfAdmin(currentUser: FirebaseUser)
    }
}