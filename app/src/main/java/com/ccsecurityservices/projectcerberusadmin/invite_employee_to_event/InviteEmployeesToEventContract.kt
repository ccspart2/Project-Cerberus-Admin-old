package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee

interface InviteEmployeesToEventContract {

    interface InviteEmployeesToEventView {
        fun updateEmployeeList()
        fun populateName(name: String)
        fun populateFinishBTN(listSize: Int, headcount: Int)
        fun popUpMessage(title: String, msg: String)
        fun changeBTNColor(completed: Boolean)
        fun returnToAddEvent(invitedEmployees: MutableList<Employee>)
        fun showLoading(state: Boolean)
    }

    interface InviteEmployeesToEventPresenter {
        fun getEmployeesFromFireBase()
        fun retrieveEventObjectFromIntent(intent: Intent)
        fun checkCapacityMet()
    }
}