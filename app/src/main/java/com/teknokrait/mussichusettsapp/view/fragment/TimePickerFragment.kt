package com.teknokrait.mussichusettsapp.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker

import com.teknokrait.mussichusettsapp.R
import kotlinx.android.synthetic.main.fragment_time_picker.*
import org.threeten.bp.LocalDate
import android.widget.CalendarView.OnDateChangeListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.CalendarView
import android.widget.Toast
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"

class TimePickerFragment : Fragment() {

    private var localTime: LocalTime? = null
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_time_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //timePicker.setIs24HourView(true)
        if (localTime == null)
            localTime = LocalTime.of(0, 0)

        if (Build.VERSION.SDK_INT >= 23) {
            timePicker.hour = localTime?.hour!!;
            timePicker.minute = localTime?.minute!!;
        } else {
            timePicker.currentHour = localTime?.hour!!;
            timePicker.currentMinute = localTime?.minute!!;
        }

        timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { _, selectedHour, selectedMinute ->
            // your logic
            localTime = LocalTime.of(selectedHour, selectedMinute)
            Toast.makeText(context, "Test : " + selectedHour.toString(), Toast.LENGTH_LONG).show()
        })

        tvSaveDateTime.setOnClickListener {
            if (localTime != null) {
                listener?.onTimePicker(localTime!!)
            } else {
                Toast.makeText(context, "Please, pick the time", Toast.LENGTH_LONG).show()
            }
        }
    }


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
        fun onTimePicker(localTime: LocalTime)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
