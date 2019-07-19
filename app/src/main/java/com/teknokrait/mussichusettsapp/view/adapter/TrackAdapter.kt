package com.teknokrait.mussichusettsapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.model.Track

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */
class TrackAdapter(tracks: List<Track>?) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    private var tracksList = ArrayList<Track>()

    init {
        if (tracks == null)
            this.tracksList = ArrayList()
        else
            this.tracksList = tracks as ArrayList<Track>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_song,
                parent, false)
        return TrackViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tracksList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackItem = tracksList[position]
        holder.trackListItem(trackItem)
    }

    fun addTracks(tracks: List<Track>) {
        val initPosition = tracksList.size
        tracksList.addAll(tracks)
        notifyItemRangeInserted(initPosition, tracksList.size)
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var trackImage = itemView.findViewById<ImageView>(R.id.track_image)!!
        private var trackName = itemView.findViewById<TextView>(R.id.track_name)!!
        private var artistName = itemView.findViewById<TextView>(R.id.artist_name)!!

        fun trackListItem(track: Track) {
            trackName.text = track.name
            artistName.text = track.artistName
        }
    }
}