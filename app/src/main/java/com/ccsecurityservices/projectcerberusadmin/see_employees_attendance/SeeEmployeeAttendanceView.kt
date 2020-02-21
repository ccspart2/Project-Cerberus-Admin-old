package com.ccsecurityservices.projectcerberusadmin.see_employees_attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_employee_attendance.*

class SeeEmployeeAttendanceView : AppCompatActivity(),
    SeeEmployeeAttendanceContract.SeeEmployeeAttendanceView {

    private lateinit var presenter: SeeEmployeeAttendancePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_employee_attendance)

        presenter = SeeEmployeeAttendancePresenter(this)
        presenter.retrieveFromIntent(intent)
        presenter.getEventList()
    }

    override fun populateView(activityLabel: String) {
        see_employee_attendance_activity_label.text = activityLabel
    }
}
