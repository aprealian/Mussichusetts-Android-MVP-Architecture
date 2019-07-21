package com.teknokrait.mussichusettsapp.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.view.fragment.TimePickerFragment
import android.widget.FrameLayout
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.util.getScreenDimension
import com.teknokrait.mussichusettsapp.view.fragment.DatePickerFragment
import kotlinx.android.synthetic.main.activity_calendar_picker.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class DatetTimePickerActivity : AppCompatActivity(), DatePickerFragment.OnFragmentInteractionListener, TimePickerFragment.OnFragmentInteractionListener {

    private var selectedDateTime:LocalDateTime? = null
    private var selectedDate:LocalDate? = null
    private var selectedTime:LocalTime? = null

    override fun onTimePicker(localTime: LocalTime) {
        //todo : save the datetime and back to create event
        //Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        this.selectedTime = localTime
        selectedDateTime = LocalDateTime.of(selectedDate,selectedTime)

        val resultIntent = Intent()
        resultIntent.putExtra(Constants.TAG_SELECTED_DATE, selectedDateTime.toString())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onDatePicker(localDate: LocalDate) {
        //todo : change fragment to time picker
        //and save the selected date
        this.selectedDate = localDate
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.frame_container, TimePickerFragment(), "time_picker")
            .addToBackStack("time_picker")
            .commit();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datet_time_picker)
        initScreen()
        initData(savedInstanceState)

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.frame_container, DatePickerFragment(), "date_picker")
            .commit();
    }

    private fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras != null && extras.getString(Constants.TAG_SELECTED_DATE) != null) {
                val strDateTime = extras.getString(Constants.TAG_SELECTED_DATE)
                selectedDateTime = LocalDateTime.parse(strDateTime)
                selectedDate = selectedDateTime?.toLocalDate()
                selectedTime = selectedDateTime?.toLocalTime()
            }
        }
    }

    private fun initScreen() {
        val screenDimension = getScreenDimension(this)
        val params = FrameLayout.LayoutParams(screenDimension[0] , screenDimension[1])
        llMain.setLayoutParams(params)
    }

}
