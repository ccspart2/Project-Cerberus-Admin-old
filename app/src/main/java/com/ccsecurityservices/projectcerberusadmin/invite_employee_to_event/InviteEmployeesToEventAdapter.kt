package com.ccsecurityservices.projectcerberusadmin.invite_employee_to_event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import kotlinx.android.synthetic.main.invite_employee_item.view.*

class InviteEmployeesToEventAdapter(private val presenter: InviteEmployeesToEventPresenter) :
    RecyclerView.Adapter<InviteEmployeesToEventAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.invite_employee_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(presenter.getEmployee(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var currentEmployee: Employee

        init {
            itemView.invite_employee_item_checkBox_BTN.setOnClickListener {
                if (!itemView.invite_employee_item_checkBox_BTN.isChecked) {
                    presenter.removeEmployeeFromInvitationList(this.currentEmployee)
                } else {
                    if(!presenter.addEmployeeToInvitationList(this.currentEmployee))
                    {
                        itemView.invite_employee_item_checkBox_BTN.isChecked = false
                    }
                }
            }
        }

        fun setData(EMP: Employee?) {
            if (EMP != null) {
                val fullName = "${EMP.firstName} ${EMP.lastName}"
                itemView.invite_employee_item_name_label.text = fullName
            }
            this.currentEmployee = EMP!!
        }
    }
}