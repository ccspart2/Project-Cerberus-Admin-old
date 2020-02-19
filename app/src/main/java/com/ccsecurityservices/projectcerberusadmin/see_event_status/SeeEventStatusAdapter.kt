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
        }
    }
}