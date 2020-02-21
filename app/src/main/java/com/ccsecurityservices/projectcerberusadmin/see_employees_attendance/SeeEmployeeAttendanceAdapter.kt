package com.ccsecurityservices.projectcerberusadmin.see_employees_attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import kotlinx.android.synthetic.main.see_all_generic_list_item.view.*

class SeeEmployeeAttendanceAdapter(private val presenter: SeeEmployeeAttendancePresenter) :
    RecyclerView.Adapter<SeeEmployeeAttendanceAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_generic_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(presenter.getEvent(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(event: Event?) {
            if(event != null)
            {
                itemView.see_all_generics_item_name_label.text = event.name
            }
        }
    }
}