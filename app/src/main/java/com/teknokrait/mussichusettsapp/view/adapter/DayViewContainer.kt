package com.teknokrait.mussichusettsapp.view.adapter

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.view_calendar_day.view.*

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.calendarDayText

    // Without the kotlin android extensions plugin
    // val textView = view.findViewById<TextView>(R.id.calendarDayText)
}