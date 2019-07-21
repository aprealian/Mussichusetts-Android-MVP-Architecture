package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.view.activity.CalendarPickerActivity
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*
import android.app.Activity
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.view.activity.DatetTimePickerActivity
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

class CreateFragment : BaseFragment() {

    //private var selectedDate:Date? = null
    private var selectedDate:Date? = null
    private var selectedDateTime:org.threeten.bp.LocalDateTime? = null
    private var isClickDateEnable:Boolean = true

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_create
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
                RealmManager.createEventDao()?.save(Event(etEventName.text.toString(), selectedDate!!))
                Toast.makeText(context, "Create Event Success", Toast.LENGTH_LONG).show()
                etEventDate.setText("")
                etEventName.setText("")
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
                            //calendar.set(Calendar.HOUR_OF_DAY, hours)

                            selectedDate = calendar.time
                            val formatter = SimpleDateFormat("hh:mm a, dd-MMM-yyyy")
                            etEventDate.setText(formatter.format(selectedDate))
                        }

                    }
                }
            }
        }
    }

}