package com.ccsecurityservices.projectcerberusadmin.see_all_locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccsecurityservices.projectcerberusadmin.Data_Items.SecLocation
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_all_generic_list_item.view.*

class SeeAllLocationsAdapter(private val seeAllLocationsPresenter: SeeAllLocationsPresenter) :
    RecyclerView.Adapter<SeeAllLocationsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.see_all_generic_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seeAllLocationsPresenter.numberOfItems()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = seeAllLocationsPresenter.getLocation(position)
        holder.setData(location, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var currentLocation: SecLocation? = null
        private var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                seeAllLocationsPresenter.setupNavToLocationDetails(currentLocation)
            }
        }

        fun setData(location: SecLocation?, position: Int) {
            if (location != null) {
                itemView.see_all_generics_item_name_label.text = location.name
            }
            this.currentLocation = location
            this.currentPosition = position
        }
    }
}