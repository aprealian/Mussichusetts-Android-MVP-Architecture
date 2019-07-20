package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.view.adapter.DayViewContainer
import com.teknokrait.mussichusettsapp.view.adapter.MonthViewContainer
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*

class CalendarFragment : BaseFragment() {

    val currentDate:Date? = Calendar.getInstance().getTime()
    val currentYear:Int? = 2019
    val currentMont:Int? = 7
    val currentDay:Int? = 20

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_calendar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarEvent()
        initLoadEvent()
    }

    private fun initLoadEvent() {
        Toast.makeText(context, "Total Event : "+RealmManager.createEventDao()?.count().toString(), Toast.LENGTH_LONG).show()
    }

    private fun initCalendarEvent() {
        // Generate calendar date range
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(12)
        val lastMonth = currentMonth.plusMonths(12)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        // Common configurations for both modes.
        calendarView.inDateStyle = InDateStyle.ALL_MONTHS
        calendarView.outDateStyle = OutDateStyle.END_OF_ROW
        calendarView.scrollMode = ScrollMode.PAGED
        calendarView.orientation = RecyclerView.HORIZONTAL
        calendarView.maxRowCount = 6
        calendarView.hasBoundaries = true


        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = month.yearMonth.month.name
                container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.font2))
                //container.textView.setTypeface(null, Typeface.BOLD);
            }
        }


        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val selectedDate = java.sql.Date.valueOf(day.date.toString())
                container.textView.text = day.date.dayOfMonth.toString()

                //Check is event dates
                val count = RealmManager.createEventDao()?.countEventByDate(selectedDate)
                if(!(count == null || count <= 0)){
                    Toast.makeText(context, count.toString(), Toast.LENGTH_LONG).show()
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    container.textView.setBackground(ContextCompat.getDrawable(context!!, R.drawable.circle_orange))
                } else if(DateUtils.isToday(selectedDate.time)){
                    //check is current date
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                } else if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.font3))
                } else {
                    container.textView.setTextColor(Color.GRAY)
                }
            }
        }

        // Select current date
        calendarView.smoothScrollToDate(LocalDate.now(), DayOwner.THIS_MONTH)
    }

}
