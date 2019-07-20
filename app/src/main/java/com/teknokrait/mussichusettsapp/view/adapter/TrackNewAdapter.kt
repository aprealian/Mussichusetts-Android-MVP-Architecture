package com.teknokrait.mussichusettsapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Track
import java.util.ArrayList

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
class TrackNewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var trackList: MutableList<Track>? = null
    var listener:OnWishlist? = null

    override fun getItemCount(): Int {
        if(trackList == null) trackList = ArrayList()
        return trackList!!.size
    }

    internal constructor(articleList: List<Track>?) {
        setTrackList(articleList)
    }

    internal constructor(listener: OnWishlist, articleList: List<Track>?) {
        setTrackList(articleList)
        this.listener = listener
    }

    internal constructor(mRecyclerView: RecyclerView, trackList: MutableList<Track>) {
        setTrackList(trackList)
    }

    private fun setTrackList(trackList: List<Track>?) {
        if (trackList == null) {
            this.trackList = ArrayList<Track>()
        } else {
            this.trackList = trackList as MutableList<Track>?
        }
    }

    internal fun addTrackList(trackList: List<Track>?) {
        if (this.trackList == null)
            this.trackList = ArrayList<Track>()

        if (trackList != null)
            this.trackList!!.addAll(trackList);
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ARTICLE) {
            TrackViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_song, viewGroup, false))
        } else if (viewType == VIEW_TYPE_EMPTY) {
            ListEmptyViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_list_empty, viewGroup, false))
        } else if (viewType == VIEW_TYPE_LOADING) {
            LoadingViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_loading, viewGroup, false))
        } else {
            TrackViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_song, viewGroup, false))
        }
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ARTICLE) {
            (holder as TrackViewHolder).bind(listener, trackList!![position])
        } else if (getItemViewType(position) == VIEW_TYPE_EMPTY) {
            (holder as ListEmptyViewHolder).bind(trackList!![position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (trackList != null && trackList!![position].itemType == VIEW_TYPE_ARTICLE) {
            VIEW_TYPE_ARTICLE
        } else if (trackList != null && trackList!![position].itemType == VIEW_TYPE_EMPTY) {
            VIEW_TYPE_EMPTY
        } else if (trackList != null && trackList!![position].itemType == VIEW_TYPE_LOADING) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ARTICLE
        }
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var trackImage = itemView.findViewById<ImageView>(R.id.track_image)!!
        private var trackName = itemView.findViewById<TextView>(R.id.track_name)!!
        private var artistName = itemView.findViewById<TextView>(R.id.artist_name)!!
        private var ivLike = itemView.findViewById<ImageView>(R.id.ivLike)!!

        fun bind(listener: OnWishlist?, track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName

            checkState(track)

            ivLike.setOnClickListener {
                onClickWishlist(track)
                listener?.onClickWishlist(track)
                //RxBus.publish(100, track)
            }
        }

        fun onClickWishlist(track: Track) {
            RealmManager.open()
            if (RealmManager.createTrackDao()?.loadBy(track.trackId) != null){
                //track.deleteFromRealm()
                RealmManager.createTrackDao()?.remove(track.trackId)
                ivLike.setImageResource(R.drawable.ic_heart)
            } else {
                RealmManager.createTrackDao()?.save(track)
                ivLike.setImageResource(R.drawable.ic_heart_filled)
            }
            RealmManager.close()
        }

        fun checkState(track: Track){
            RealmManager.open()
            if (RealmManager.createTrackDao()?.loadBy(track.trackId) != null)
                ivLike.setImageResource(R.drawable.ic_heart_filled)
            else
                ivLike.setImageResource(R.drawable.ic_heart)
            RealmManager.close()
        }
    }


    internal inner class LoadingViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {}

    internal inner class ListEmptyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvNotFound: TextView

        init {
            tvNotFound = itemView.findViewById(R.id.tvNotFound)
        }

        fun bind(track: Track) {
            //tvNotFound.setText("")
        }
    }

    fun loadingOn() {
        //Add loading item
        val data = Track()
        data.itemType = VIEW_TYPE_LOADING
        trackList!!.add(data)
        notifyItemInserted(trackList!!.size - 1)
    }

    fun loadingOff() {
        //Remove loading item
        trackList!!.removeAt(trackList!!.size - 1)
        notifyItemRemoved(trackList!!.size)
    }

    fun addEmptyState() {
        //add empty
        this.trackList = ArrayList()
        (this.trackList as ArrayList<Track>).add(0, Track(-1,null,-1,"",-1,"",2))
        notifyDataSetChanged()
    }

    fun removeEmptyState() {
        //Remove loading item
        if(itemCount > 0 && trackList?.get(0)?.itemType == VIEW_TYPE_EMPTY){
            trackList?.removeAt(0)
            notifyDataSetChanged()
        }
    }

    fun checkErrorState(throwable: Throwable) {
        //Check error
        loadingOff()
        if (getItemCount() == 0) {
            (this.trackList as ArrayList<Track>).add(0, Track(-1,"Sorry, something wrong..",-1,"",-1,"",2))
            notifyDataSetChanged()
        }
    }

    interface OnWishlist{
        fun onClickWishlist(track: Track)
    }

    companion object {
        private val VIEW_TYPE_ARTICLE = 0//items
        private val VIEW_TYPE_LOADING = 1//loading
        private val VIEW_TYPE_EMPTY = 2//empty
    }
}