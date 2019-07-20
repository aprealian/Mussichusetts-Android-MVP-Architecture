package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.view.adapter.DayViewContainer
import com.teknokrait.mussichusettsapp.view.adapter.MonthViewContainer
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*

class CalendarFragment : BaseFragment() {

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_calendar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarEvent()
    }

    private fun initCalendarEvent() {
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)


        // Common configurations for both modes.
        calendarView.inDateStyle = InDateStyle.ALL_MONTHS
        calendarView.outDateStyle = OutDateStyle.END_OF_ROW
        calendarView.scrollMode = ScrollMode.PAGED
        calendarView.orientation = RecyclerView.HORIZONTAL

        val monthToWeek = false
        if (monthToWeek) {
            // One row calendar for week mode
            calendarView.maxRowCount = 1
            calendarView.hasBoundaries = false
        } else {
            // Six row calendar for month mode
            calendarView.maxRowCount = 6
            calendarView.hasBoundaries = true
        }



        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
//                container.textView.text = titleFormatter.format(month.yearMonth)
//                // Setup each header day text if we have not done that already.
//                if (container.legendLayout.tag == null) {
//                    container.legendLayout.tag = month.yearMonth
//                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
//                        tv.text = daysOfWeek[index].name.first().toString()
//                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
//                        //tv.setTextColorRes(R.color.example_6_black)
//                    }
//                }

                container.textView.text = month.yearMonth.month.name
                container.textView.setTextColor(Color.RED)
            }
        }


        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(Color.RED)
                } else {
                    container.textView.setTextColor(Color.GRAY)
                }
            }
        }
    }

}
