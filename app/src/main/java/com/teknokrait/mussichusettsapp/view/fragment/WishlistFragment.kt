package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.widget.NestedScrollView

import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.presenter.TracksPresenter
import com.teknokrait.mussichusettsapp.view.adapter.TrackNewAdapter
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wishlist.*

class WishlistFragment : BaseFragment(), TracksPresenter.View  {

    private var page = 0
    private var isMore = true
    private var isLoading = false

    private var tracksPresenter: TracksPresenter? = null
    private var newsAdapter: TrackNewAdapter? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_wishlist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open()
        initTrackRequest()
        //initAdapter()
    }

//    private fun initAdapter() {
//        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
//            if (v.getChildAt(v.childCount - 1) != null) {
//
//                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
//
//                    val visibleItemCount = recyclerView.linearLayoutManager!!.childCount
//                    val totalItemCount = recyclerView.linearLayoutManager!!.itemCount
//                    val pastVisiblesItems = recyclerView.linearLayoutManager!!.findFirstVisibleItemPosition()
//
//                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//                        //after reach latest data then load more data (load next page)
//                        loadMoreData()
//                    }
//                }
//            }
//        })
//    }

    private fun initTrackRequest() {
        page = 1
        isLoading = false
        isMore = true
        newsAdapter = TrackNewAdapter( null)
        recyclerView!!.adapter = newsAdapter
        tracksPresenter = TracksPresenter(this)
        newsAdapter!!.loadingOn()
        tracksPresenter!!.getTracksFromDB(page)
    }

//    private fun loadMoreData() {
//        if (isMore && !isLoading) {
//            newsAdapter!!.loadingOn()
//            isLoading = true
//            Handler().postDelayed({ tracksPresenter!!.getTracksFromDB(page) }, 5000)
//        }
//    }

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

    override fun onDetach() {
        super.onDetach()
        tracksPresenter?.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmManager.close()
    }

}