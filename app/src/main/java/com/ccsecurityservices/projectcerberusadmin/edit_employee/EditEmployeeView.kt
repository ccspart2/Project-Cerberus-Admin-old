package com.ccsecurityservices.projectcerberusadmin.edit_employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.ccsecurityservices.projectcerberusadmin.R

class EditEmployeeView : AppCompatActivity() , EditEmployeeContract.EditEmployeeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_employee_layout)
    }

    override fun populateView(EMP: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun NavOut() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
