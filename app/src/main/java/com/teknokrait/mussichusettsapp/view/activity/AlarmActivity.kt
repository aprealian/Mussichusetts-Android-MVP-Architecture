package com.teknokrait.mussichusettsapp.view.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teknokrait.mussichusettsapp.R
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.teknokrait.mussichusettsapp.alarm.AlarmBroadCastReceiver
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import timber.log.Timber
import java.text.SimpleDateFormat


class AlarmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.TAG_EVENT)) {
            val gson = Gson()
            val strObj = intent.getStringExtra(Constants.TAG_EVENT)
            val event = gson.fromJson(strObj, Event::class.java)
            val formatter = SimpleDateFormat("hh:mm a, dd-MMM-yyyy")
            val eventDateString = formatter.format(event.eventDate)
            tvEventName.text = event.eventName
            tvEventDate.text = eventDateString
        }
    }



}
