package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.net.Uri

interface HomePageContract {

    interface HomePageView {
        fun populateUser(name: String, photoURL: String)
        fun navToSignIn()
        fun updateImageView(selectedImageUri: Uri)
        fun displayToastMessage(msg : String)
        fun displayLoadingWidget(state: Boolean)
    }

    interface HomePagePresenter {
        fun retrieveEmployeeFromAuth()
        fun logOut()
        fun createIntentForProfilePic(): Intent
        fun updateProfilePicInStorage(data: Intent?)
    }
}