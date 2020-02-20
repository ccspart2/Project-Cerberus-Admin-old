package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import kotlinx.android.synthetic.main.see_event_status_employee_item.view.*

class SeeEventStatusAdapter(private val seeEventStatusPresenter: SeeEventStatusPresenter) :
    RecyclerView.Adapter<SeeEventStatusAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_event_status_employee_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seeEventStatusPresenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val emp = seeEventStatusPresenter.getEmployee(position)
        holder.setData(emp)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(emp: Employee) {
            itemView.see_event_status_employee_item_name_label.text =
                emp.firstName.plus(" ").plus(emp.lastName)
            when (seeEventStatusPresenter.employeeAttendanceStatus(emp.id)) {
                "Invited" -> {
                    prepareInvited(emp)
                }
                "Accepted" -> {
                    prepareAccepted(emp)
                }
                "Declined" -> {
                    prepareDeclined()
                }
                else -> {
                    prepareNotInvited(emp)
                }
            }
        }

        private fun prepareNotInvited(employee: Employee) {
            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.non_invited_status_circle)
            itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_enabled)
            itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_disabled)
            togglePlus(true, employee)
        }

        private fun prepareDeclined() {
            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.declined_status_circle)
            itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_disabled)
            itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_disabled)
            itemView.see_event_status_employee_item_add_btn.setOnClickListener(null)
            itemView.see_event_status_employee_item_subtract_btn.setOnClickListener(null)
        }

        private fun prepareAccepted(employee: Employee) {
            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.accepted_status_circle)
            itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_disabled)
            itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_enabled)
            toggleMinus(true, employee)
        }

        private fun prepareInvited(employee: Employee) {
            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.invite_status_circle)
            itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_disabled)
            itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_enabled)
            toggleMinus(true, employee)
        }

        private fun togglePlus(state: Boolean, obj: Employee?) {
            if (state) {
                itemView.see_event_status_employee_item_add_btn.setOnClickListener {
                    itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_enabled)
                    itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_disabled)

                    when (seeEventStatusPresenter.modifyChanges(obj!!)) {
                        "TEMP_ADD" -> {
                            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.pending_add_status_circle)
                        }
                        "ACCEPTED" -> {
                            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.accepted_status_circle)
                        }
                        "PENDING" -> {
                            itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.invite_status_circle)
                        }
                    }
                    togglePlus(false, null)
                    toggleMinus(true, obj)
                }
            } else {
                itemView.see_event_status_employee_item_add_btn.setOnClickListener(null)
            }
        }

        private fun toggleMinus(state: Boolean, obj: Employee?) {
            if (state) {
                itemView.see_event_status_employee_item_subtract_btn.setOnClickListener {
                    itemView.see_event_status_employee_item_subtract_btn.setImageResource(R.drawable.minus_icon_disabled)
                    itemView.see_event_status_employee_item_add_btn.setImageResource(R.drawable.plus_icon_enabled)

                    val result = seeEventStatusPresenter.modifyChanges(obj!!)
                    if (result == "UNINVITED") {
                        itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.non_invited_status_circle)
                    } else if (result == "TEMP_DELETE") {
                        itemView.see_event_status_employee_item_semaphore_circle.setImageResource(R.drawable.pending_delete_status_circle)
                    }
                    toggleMinus(false, null)
                    togglePlus(true, obj)
                }
            } else {
                itemView.see_event_status_employee_item_subtract_btn.setOnClickListener(null)
            }
        }
    }
}