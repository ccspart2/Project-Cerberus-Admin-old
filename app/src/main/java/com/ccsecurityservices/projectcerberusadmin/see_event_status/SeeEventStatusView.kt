package com.ccsecurityservices.projectcerberusadmin.see_event_status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_event_status.*

class SeeEventStatusView : AppCompatActivity(), SeeEventStatusContract.SeeEventStatusView {

    private lateinit var presenter: SeeEventStatusPresenter
    private lateinit var adapter: SeeEventStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_event_status)

        presenter = SeeEventStatusPresenter(this)
        presenter.retrieveData(intent)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        see_event_status_recycler_view.layoutManager = layoutManager

        adapter = SeeEventStatusAdapter(presenter)
        see_event_status_recycler_view.adapter = adapter

        see_event_status_refresh_BTN.setOnClickListener {
            presenter.updateFireBaseDB()
        }
    }

    override fun populateActivityLabel(eventName: String) {
        see_event_status_activity_label.text =
            getString(R.string.see_event_status_activity_label_text).plus(" $eventName")
    }

    override fun updateList() {
        presenter.sortByAttendance()
        adapter.notifyDataSetChanged()
    }

    override fun populateHeadcount(eventHeadCount: Int, currentHeadcount: Int) {
        see_event_status_headcount_label.text =
            getString(R.string.see_event_status_headcount_label_text).plus(" $currentHeadcount/$eventHeadCount")
    }

    override fun navBack() {
        finish()
    }
}
