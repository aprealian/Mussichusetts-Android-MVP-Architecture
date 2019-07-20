package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.view.View


import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.MessageEvent
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.presenter.TracksPresenter
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.view.adapter.TrackNewAdapter
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wishlist.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode




class WishlistFragment : BaseFragment(), TracksPresenter.View, TrackNewAdapter.OnWishlist {

    private var page = 0
    private var isMore = true
    private var isLoading = false

    private var tracksPresenter: TracksPresenter? = null
    private var newsAdapter: TrackNewAdapter? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_wishlist
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isEventBusNeeded = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open()
        initTrackRequest()
        wishlistListener()
    }

    private fun wishlistListener() {
//        RxBusOld.subscribe(Consumer {
//            if (it is MessageEvent) {
//                //Toast.makeText(context, "new wishlist", Toast.LENGTH_LONG).show()
//                initTrackRequest()
//            }
//        })

//        RxBus.subscribe(subject = 100, lifecycle = Track , action = Consumer {
//            //Toast.makeText(context, "new wishlist", Toast.LENGTH_LONG).show()
//            initTrackRequest()
//        })
    }

    private fun initTrackRequest() {
        page = 1
        isLoading = false
        isMore = true
        newsAdapter = TrackNewAdapter( this, null)
        recyclerView!!.adapter = newsAdapter
        tracksPresenter = TracksPresenter(this)
        newsAdapter!!.loadingOn()
        tracksPresenter!!.getTracksFromDB(page)
    }

    override fun onSuccessGetTracks(trackList: List<Track>) {
        newsAdapter!!.loadingOff()
        newsAdapter!!.addTrackList(trackList)
        newsAdapter!!.notifyDataSetChanged()

        page++
        isLoading = false

        if (newsAdapter!!.itemCount <= 0) {
            newsAdapter!!.addEmptyState()
        }

        if (trackList.isEmpty()) {
            isMore = false
        }
    }

    override fun onErrorGetTracks(throwable: Throwable) {
        newsAdapter?.checkErrorState(throwable)
    }

    override fun onClickWishlist(track: Track) {
        //Toast.makeText(context, "wishlist listener", Toast.LENGTH_LONG).show()
        //RxBus.publish(100, track)
        EventBus.getDefault().post(MessageEvent(Constants.TAG_WISH_LIST, track))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if(event.tag.equals(Constants.TAG_WISH_LIST))initTrackRequest()
    }

    override fun onDetach() {
        super.onDetach()
        tracksPresenter?.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmManager.close()
    }

}

