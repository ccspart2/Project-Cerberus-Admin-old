package com.ccsecurityservices.projectcerberusadmin.see_employees_attendance

import android.content.Intent

interface SeeEmployeeAttendanceContract {
    interface SeeEmployeeAttendanceView {
        fun populateView(activityLabel: String)
    }

    interface SeeEmployeeAttendancePresenter {
        fun retrieveFromIntent(intent: Intent)
        fun getEventList()
    }
}