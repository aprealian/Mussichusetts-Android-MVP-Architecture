package com.teknokrait.mussichusettsapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.model.Event
import kotlinx.android.synthetic.main.list_item_event.view.*
import org.threeten.bp.DateTimeUtils
import java.text.SimpleDateFormat
import android.annotation.SuppressLint
import com.teknokrait.mussichusettsapp.util.VectorDrawableUtils

/**
 * Created by Aprilian Nur Wakhid Daini on 7/21/2019.
 */
class EventTimeLineAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventTimeLineAdapter.TimeLineViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return TimeLineViewHolder(layoutInflater.inflate(R.layout.list_item_event, parent, false), viewType)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val event = eventList[position]

        setMarker(holder, R.drawable.ic_marker, R.color.grey)
        holder.date.visibility = View.VISIBLE
        val formatter = SimpleDateFormat("hh:mm a, dd-MMM-yyyy")
        val s = formatter.format(event.eventDate)
        holder.date.text = s
        holder.message.text = event.eventName
    }

    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {
        holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId)
    }

    override fun getItemCount() = eventList.size

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.tvEventDate
        val message = itemView.tvEventName
        val timeline = itemView.timeline

        init {
            timeline.initLine(viewType)
        }
    }

}