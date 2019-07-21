package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

private const val ARG_PARAM1 = "param1"

class DatePickerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var currentSelectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarPicker()
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
                container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.font2))
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
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    container.textView.setBackground(ContextCompat.getDrawable(context!!, R.drawable.circle_red))
                } else if (day.owner == DayOwner.THIS_MONTH && selectedDate < currentDate) {
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.grey1))
                    container.textView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
                } else if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.font2))
                    container.textView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
                } else {
                    container.textView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    container.textView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
                }

                container.textView.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH && selectedDate >= currentDate){
//                        val resultIntent = Intent()
//                        resultIntent.putExtra(Constants.TAG_SELECTED_DATE, day.date.toString())
//                        activity?.setResult(Activity.RESULT_OK, resultIntent)
//                        activity?.finish()
                        //TODO : from activity change to fragemnt listener
                        listener?.onDatePicker(day.date)
                    }
                }

            }
        }

        // current selcteded date
        //if(currentSelectedDate != null) calendarView.smoothScrollToDate(LocalDate.now(), DayOwner.THIS_MONTH)
    }




















    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onDatePicker(uri)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onDatePicker(localDate: LocalDate)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
