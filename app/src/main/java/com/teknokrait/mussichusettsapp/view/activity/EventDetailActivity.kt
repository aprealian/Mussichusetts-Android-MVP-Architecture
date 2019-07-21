package com.teknokrait.mussichusettsapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.view.adapter.EventTimeLineAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import java.util.*
import kotlin.collections.ArrayList

class EventDetailActivity : AppCompatActivity() {

    private var selectedDate:Date? = null

    private lateinit var mAdapter: EventTimeLineAdapter
    private var mDataList = ArrayList<Event>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        getSelectedDate()
        initRecyclerView()
        checkEvent()
        initOnClick()
    }

    private fun initOnClick() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkEvent() {
        if (selectedDate != null){
            val events = RealmManager.createEventDao()?.loadEventsByDate(selectedDate!!)
            if (events != null){
                //mDataList = events as ArrayList<Event>
                mAdapter = EventTimeLineAdapter(events)
                recyclerView.adapter = mAdapter
            }
        }
    }

    private fun getSelectedDate() {
        val extras = intent.extras
        if (extras?.get(Constants.TAG_DATE) != null) {
            selectedDate = extras.get(Constants.TAG_DATE) as Date
        }
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = EventTimeLineAdapter(mDataList)
        recyclerView.adapter = mAdapter
    }


}
