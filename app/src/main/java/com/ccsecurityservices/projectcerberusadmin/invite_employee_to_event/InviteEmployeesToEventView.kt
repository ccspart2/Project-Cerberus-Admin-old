package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.invite_employees_to_event.*

class InviteEmployeesToEventView : AppCompatActivity(),
    InviteEmployeesToEventContract.InviteEmployeesToEventView {

    private lateinit var presenter: InviteEmployeesToEventPresenter
    private lateinit var adapter: InviteEmployeesToEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_employees_to_event)

        presenter = InviteEmployeesToEventPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        invite_employee_recycler_view.layoutManager = layoutManager

        adapter = InviteEmployeesToEventAdapter(presenter)
        invite_employee_recycler_view.adapter = adapter

        presenter.getEmployeesFromFireBase()
    }

    override fun updateEmployeeList() {
        adapter.notifyDataSetChanged()
    }
}
