package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.add_new_event.AddNewEventView
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.edit_employee.EditEmployeeView
import kotlinx.android.synthetic.main.invite_employees_to_event.*
import kotlinx.android.synthetic.main.see_all_employees.*
import java.io.Serializable

class InviteEmployeesToEventView : AppCompatActivity(),
    InviteEmployeesToEventContract.InviteEmployeesToEventView {

    private lateinit var presenter: InviteEmployeesToEventPresenter
    private lateinit var adapter: InviteEmployeesToEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_employees_to_event)

        //fix This, Lazy
        invite_employee_finish_BTN.setBackgroundResource(android.R.drawable.btn_default)

        presenter = InviteEmployeesToEventPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        invite_employee_recycler_view.layoutManager = layoutManager

        adapter = InviteEmployeesToEventAdapter(presenter)
        invite_employee_recycler_view.adapter = adapter

        presenter.retrieveEventObjectFromIntent(intent)

        presenter.getEmployeesFromFireBase()

        invite_employee_finish_BTN.setOnClickListener {
            presenter.checkCapacityMet()
        }
    }

    override fun updateEmployeeList() {
        adapter.notifyDataSetChanged()
    }

    override fun populateName(name: String) {
        val labelText = "Invitations for : $name"
        invite_employee_activity_label.text = labelText
    }

    override fun populateFinishBTN(listSize: Int, headcount: Int) {
        val btnText = "Employees Invited: $listSize/$headcount"
        invite_employee_finish_BTN.text = btnText
    }

    override fun popUpMessage(title: String, msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setNeutralButton("OK") { _: DialogInterface?, _: Int -> }
        builder.show()
    }

    override fun changeBTNColor(completed: Boolean) {
        if (completed) {
            invite_employee_finish_BTN.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.Default_text_color
                )
            )
        } else {
            invite_employee_finish_BTN.setBackgroundResource(android.R.drawable.btn_default)
        }
    }

    override fun returnToAddEvent(invitedEmployees: MutableList<Employee>) {
        val intent = Intent().apply {
            putExtra(
                "event_invitation_complete",
                invitedEmployees as Serializable
            )
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    override fun showLoading(state: Boolean) {
        if (state) {
            invite_employee_loading_widget.visibility = View.VISIBLE
        } else {
            invite_employee_loading_widget.visibility = View.GONE
        }
    }
}
