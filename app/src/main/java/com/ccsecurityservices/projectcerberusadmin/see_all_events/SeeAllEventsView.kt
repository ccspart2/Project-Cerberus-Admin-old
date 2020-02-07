package com.ccsecurityservices.projectcerberusadmin.see_all_events

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.add_new_event.AddNewEventView
import com.ccsecurityservices.projectcerberusadmin.data_items.Event
import kotlinx.android.synthetic.main.see_all_events.*

class SeeAllEventsView : AppCompatActivity(), SeeAllEventsContract.SeeAllEventsView {

    private lateinit var presenter: SeeAllEventsPresenter
    private lateinit var adapter: SeeAllEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_all_events)

        presenter = SeeAllEventsPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        see_all_events_recycler_view.layoutManager = layoutManager

        adapter = SeeAllEventsAdapter(presenter)
        see_all_events_recycler_view.adapter = adapter

        presenter.getEventList()

        see_all_events_add_event_BTN.setOnClickListener {
            val navIntent = Intent(this, AddNewEventView::class.java)
            startActivity(navIntent)
        }
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun navToEventDetails(event: Event) {
        Toast.makeText(this, event.name, Toast.LENGTH_LONG).show()
    }

    override fun displayLoading(state: Boolean) {
        if (state) {
            see_all_events_loading_widget.visibility = View.VISIBLE
        } else {
            see_all_events_loading_widget.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachListener()
    }
}
