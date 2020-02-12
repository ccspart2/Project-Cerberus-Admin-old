package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.content.Intent
import android.net.Uri
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee

interface SeeEmployeesDetailsContract {

    interface SeeEmployeesDetailsView {
        fun populateFields(EMP: Employee)
        fun deleteResult(result: Boolean)
        fun updateProfilePicFromPicker(uri: Uri?)
        fun downloadPic(imageUrl: String)
        fun showToastMessages(msg: String)
        fun showLoading(state: Boolean)
        fun navToEditEmployee(EMP: Employee)
        fun displayActiveEmployeeDialog()
    }

    interface SeeEmployeesDetailsPresenter {
        fun retrieveEmployeeObject(EMP: Employee)
        fun prepareForDelete()
        fun prepareForEdit()
        fun createIntentForProfilePic(): Intent
        fun retrieveProfilePic(data: Intent?)
    }
}