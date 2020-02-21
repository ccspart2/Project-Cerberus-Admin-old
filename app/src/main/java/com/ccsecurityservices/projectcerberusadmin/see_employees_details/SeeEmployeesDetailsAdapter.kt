package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Attendance
import kotlinx.android.synthetic.main.see_all_generic_list_item.view.*

class SeeEmployeesDetailsAdapter(private val presenter: SeeEmployeesDetailsPresenter) :
    RecyclerView.Adapter<SeeEmployeesDetailsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_generic_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(presenter.getAttendance(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(attendance: Attendance?) {
            itemView.see_all_generics_item_name_label.text = attendance!!.eventName
            when (attendance.status) {
                "Invited" -> {
                    itemView.see_all_employees_item_infoBTN.setImageResource(R.drawable.invite_status_circle)
                }
                "Declined" -> {
                    itemView.see_all_employees_item_infoBTN.setImageResource(R.drawable.declined_status_circle)
                }
                "Accepted" -> {
                    itemView.see_all_employees_item_infoBTN.setImageResource(R.drawable.accepted_status_circle)
                }
            }
        }
    }
}
