package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.content.Intent
import android.net.Uri
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee

interface SeeEmployeesDetailsContract {

    interface SeeEmployeesDetailsView {
        fun populateFields(EMP: Employee)
        fun deleteResult(result : Boolean)
        fun updateProfilePicFromPicker(uri: Uri?)
        fun downloadPic(imageUrl : String)
    }

    interface SeeEmployeesDetailsPresenter {
        fun retrieveEmployeeObject(EMP: Employee)
        fun deleteEmployee(): Boolean
        fun prepareForEdit(): Employee
        fun createIntentForProfilePic() : Intent
        fun retrieveProfilePic(data: Intent?)
    }
}