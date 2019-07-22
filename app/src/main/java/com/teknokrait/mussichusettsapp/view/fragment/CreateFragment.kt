package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import com.teknokrait.mussichusettsapp.util.closeKeyboard
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*
import android.app.Activity
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.view.activity.DatetTimePickerActivity
import java.text.SimpleDateFormat
import android.app.AlarmManager
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent
import com.teknokrait.mussichusettsapp.alarm.AlarmBroadCastReceiver
import com.teknokrait.mussichusettsapp.alarm.AlarmService
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import com.teknokrait.mussichusettsapp.view.activity.AlarmActivity
import com.google.gson.Gson
import com.teknokrait.mussichusettsapp.model.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber


class CreateFragment : BaseFragment() {

    //private var selectedDate:Date? = null
    private var selectedDate:Date? = null
    private var selectedAlarmDate:Date? = null
    private var selectedDateTime:org.threeten.bp.LocalDateTime? = null
    private var isClickDateEnable:Boolean = true

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_create
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isEventBusNeeded = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open()
        initCreateEvent()
    }

    private fun initCreateEvent() {
        tvCreateEvent.setOnClickListener {
            if(selectedDate == null || etEventDate.text.toString().isEmpty()){
                Toast.makeText(context, "Event Date cannot be empty", Toast.LENGTH_LONG).show()
            } else if(etEventName.text.toString().isEmpty()){
                Toast.makeText(context, "Event Name cannot be empty", Toast.LENGTH_LONG).show()
            } else {
                val event = Event(etEventName.text.toString(), selectedDate!!)
                //RealmManager.open()
                RealmManager.createEventDao()?.save(event)
                Toast.makeText(context, "Create Event Success", Toast.LENGTH_LONG).show()
                etEventDate.setText("")
                etEventName.setText("")
                //createAlarmActivity()
                //createAlarmService()
                //createAlarmReceiver()
                //val eventAlarm = RealmManager.createEventDao()?.loadEventAlarm30MinuteBefore()
                //RealmManager.close()
                closeKeyboard(this.context!!,etEventDate)
                closeKeyboard(this.context!!,etEventName)
                createAlarmReceiver(event)
                EventBus.getDefault().post(MessageEvent(Constants.TAG_CALENDAR, event))
            }
        }

        llChooseCalendar.setOnClickListener {
            if (isClickDateEnable){
                //todo: create calendar picker
                //val calendarFragment = CustomDialogFragment()
                //calendarFragment.show(childFragmentManager,"Calendar Picker")
                isClickDateEnable = false
                //val i = Intent(activity, CalendarPickerActivity::class.java)
                val i = Intent(activity, DatetTimePickerActivity::class.java)
                if(selectedDate != null){
                    //i.putExtra(Constants.TAG_SELECTED_DATE, selectedDate.toString())
                    i.putExtra(Constants.TAG_SELECTED_DATE, selectedDateTime.toString())
                }
                startActivityForResult(i, Constants.CODE_DATE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmManager.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.CODE_DATE -> {

                isClickDateEnable = true

                if (data != null) {
                    if (resultCode == Activity.RESULT_OK && data.hasExtra(Constants.TAG_SELECTED_DATE) && data.getStringExtra(Constants.TAG_SELECTED_DATE) != null) {
                        val strDateTime = data.getStringExtra(Constants.TAG_SELECTED_DATE)
                        //selectedDate = java.sql.Date.valueOf(newDate)
                        selectedDateTime = org.threeten.bp.LocalDateTime.parse(strDateTime)
                        //selectedDate = java.sql.Date.valueOf(selectedDateTime.toString())

//                        selectedDate = Date()
//                        selectedDate?.time = selectedDateTime?.toEpochSecond(OffsetDateTime.now().getOffset())!!
//                        //selectedDate?.time = selectedDateTime.
//
                        if (selectedDateTime != null){
                            val calendar = Calendar.getInstance()
                            calendar.set(
                                selectedDateTime!!.year,
                                selectedDateTime!!.monthValue,
                                selectedDateTime!!.dayOfMonth,
                                selectedDateTime!!.hour,
                                selectedDateTime!!.minute)

                            calendar.add(Calendar.MONTH, -1)
                            selectedDate = calendar.time
                            val formatter = SimpleDateFormat("hh:mm a, dd-MMM-yyyy")
                            etEventDate.setText(formatter.format(selectedDate))


                            calendar.add(Calendar.MINUTE, -30)
                            selectedAlarmDate = calendar.time
                        }

                    }
                }
            }
        }
    }


    private fun createAlarmActivity() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmActivity::class.java)
        intent.setAction("Alarm Activity")
        val pendingIntent = PendingIntent.getActivity(
            context,
            100, intent, FLAG_CANCEL_CURRENT
        )
        if (SDK_INT < Build.VERSION_CODES.M) {
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent)
        } else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager!!.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10 * 1000, pendingIntent
            )
        }
    }

    private fun createAlarmService() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmService::class.java)
        intent.setAction("Alarm Service")
        val pendingIntent = PendingIntent.getService(
            context,
            100, intent, FLAG_CANCEL_CURRENT
        )

        alarmManager!!.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent)
    }

    private fun createAlarmReceiver(event:Event?) {

        //create alarm
        if (event != null && event.eventDate != null){
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val intent = Intent(context, AlarmBroadCastReceiver::class.java)
            intent.putExtra("myAction","notify")

            val gson = Gson()
            intent.putExtra(Constants.TAG_EVENT, gson.toJson(event))

            intent.setAction("android.alarm.receiver")
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                100, intent, FLAG_CANCEL_CURRENT
            )

            val calendar = Calendar.getInstance()
            calendar.set(
                selectedDateTime!!.year,
                selectedDateTime!!.monthValue,
                selectedDateTime!!.dayOfMonth,
                selectedDateTime!!.hour,
                selectedDateTime!!.minute)
            calendar.add(Calendar.MINUTE, -30)
            //harus minus satu bulan
            calendar.add(Calendar.MONTH, -1)

//            Timber.e("cek calendar millis 1 : "+calendar.timeInMillis)
//            Timber.e("cek calendar millis 2 : "+selectedAlarmDate?.time)
//            Timber.e("cek calendar millis 3 : "+selectedDate?.time)
//            Timber.e("cek calendar millis current : "+System.currentTimeMillis())

            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, event.eventDate.time - 1800000, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, 1563789382681, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, selectedAlarmDate?.time!!, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, alarm, pendingIntent)
            //alarmManager!!.set(AlarmManager.RTC_WAKEUP, selectedDate?.time!!+1*1, pendingIntent)
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis +1*1, pendingIntent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }

}