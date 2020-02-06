package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

interface InviteEmployeesToEventContract {

    interface InviteEmployeesToEventView {
        fun updateEmployeeList()

    }

    interface InviteEmployeesToEventPresenter {
        fun getEmployeesFromFireBase()
    }
}