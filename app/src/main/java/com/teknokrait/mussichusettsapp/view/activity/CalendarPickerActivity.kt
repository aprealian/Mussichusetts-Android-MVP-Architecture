package com.teknokrait.mussichusettsapp.view.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.view.adapter.DayViewContainer
import com.teknokrait.mussichusettsapp.view.adapter.MonthViewContainer
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*
import android.R.attr.minHeight
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_calendar_picker.*
import kotlinx.android.synthetic.main.fragment_calendar.calendarView
import com.teknokrait.mussichusettsapp.util.getScreenDimension
import android.app.Activity
import android.content.Intent
import com.teknokrait.mussichusettsapp.util.Constants

class CalendarPickerActivity : AppCompatActivity() {

    //private var currentSelectedDate:LocalDate? = null
    private var currentSelectedDate:Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_picker)
        initScreen()
        initData(savedInstanceState)
        initCalendarPicker()
    }

    private fun initScreen() {
        val screenDimension = getScreenDimension(this)
        //val params = FrameLayout.LayoutParams( (screenDimension[0].toDouble()*(9/10)).toInt(), FrameLayout.LayoutParams.MATCH_PARENT)
        val params = FrameLayout.LayoutParams(screenDimension[0] , screenDimension[1])
        llMain.setLayoutParams(params)
    }

    private fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras != null && extras.getString(Constants.TAG_SELECTED_DATE) != null) {
                val strDate = extras.getString(Constants.TAG_SELECTED_DATE)
                currentSelectedDate = java.sql.Date.valueOf(strDate)
                //currentSelectedDate = LocalDate.parse(date.toString())
            }
        }
    }

    private fun initCalendarPicker() {
        // Generate calendar date range
        val currentMonth = YearMonth.now()
        //val firstMonth = currentMonth.minusMonths(12)
        val firstMonth = YearMonth.now()
        val lastMonth = currentMonth.plusMonths(12)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        // Common configurations for both modes.
        calendarView.inDateStyle = InDateStyle.ALL_MONTHS
        calendarView.outDateStyle = OutDateStyle.END_OF_ROW
        calendarView.scrollMode = ScrollMode.PAGED
        calendarView.orientation = RecyclerView.VERTICAL
        calendarView.maxRowCount = 6
        calendarView.hasBoundaries = true


        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = month.yearMonth.month.name.toString().plus(" ").plus(month.year.toString())
                container.textView.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.font2))
            }
        }

        val currentDateLocal = LocalDate.now()
        val currentDate = java.sql.Date.valueOf(currentDateLocal.toString())

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                //TODO("need to enhancement to get the best result - time complexity")
                //TODO("just use LocalDate")
                val selectedDate = java.sql.Date.valueOf(day.date.toString())
                container.textView.text = day.date.dayOfMonth.toString()

                if(currentSelectedDate != null && selectedDate.equals(currentSelectedDate)){
                    //check is current date
                    container.textView.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                    container.textView.setBackground(ContextCompat.getDrawable(applicationContext!!, R.drawable.circle_red))
                } else if (day.owner == DayOwner.THIS_MONTH && selectedDate < currentDate) {
                    container.textView.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.grey1))
                    container.textView.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                } else if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.font2))
                    container.textView.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                } else {
                    container.textView.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                    container.textView.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }

                container.textView.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH && selectedDate >= currentDate){
                        val resultIntent = Intent()
                        resultIntent.putExtra(Constants.TAG_SELECTED_DATE, day.date.toString())
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                }

            }
        }

        // current selcteded date
        //if(currentSelectedDate != null) calendarView.smoothScrollToDate(LocalDate.now(), DayOwner.THIS_MONTH)
    }
}
