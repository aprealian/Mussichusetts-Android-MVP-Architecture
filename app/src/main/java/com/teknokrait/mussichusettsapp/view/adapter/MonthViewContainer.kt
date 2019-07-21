package com.teknokrait.mussichusettsapp.view.adapter

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.view_calendar_month.view.*

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
class MonthViewContainer(view: View) : ViewContainer(view) {
    val textView = view.calendarMonthText
    val legendLayout = view.calendarDayText
}