package com.ccsecurityservices.projectcerberusadmin.See_All_Employees

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employees
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_all_employees_list_item.view.*

class SeeAllEmployeesAdapter(private val seeAllEmployeesPresenter: SeeAllEmployeesPresenter) :
    RecyclerView.Adapter<SeeAllEmployeesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_employees_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seeAllEmployeesPresenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val employee = seeAllEmployeesPresenter.getEmployee(position)
        holder.setData(employee, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(employee: Employees?, position: Int) {
            itemView.see_all_employees_item_name_label.text = employee!!.FirstName
        }
    }
}
