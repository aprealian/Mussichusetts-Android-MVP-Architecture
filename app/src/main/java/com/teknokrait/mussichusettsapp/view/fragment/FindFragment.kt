package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.presenter.TracksPresenter
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import androidx.core.widget.NestedScrollView
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.view.adapter.TrackNewAdapter

class FindFragment : BaseFragment(), TracksPresenter.View {

    private var keyword: String = "jsjsjajaj"
    private var page = 1
    private var isMore = true
    private var isLoading = false

    private var tracksPresenter: TracksPresenter? = null
    private var newsAdapter: TrackNewAdapter? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_find
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open();
        initNewsRequest()
        initAdapter()
    }

    private fun initAdapter() {
        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {

                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {

                    val visibleItemCount = recyclerView.linearLayoutManager!!.getChildCount()
                    val totalItemCount = recyclerView.linearLayoutManager!!.getItemCount()
                    val pastVisiblesItems = recyclerView.linearLayoutManager!!.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        //after reach latest data then load more data (load next page)
                        loadMoreData()
                    }
                }
            }
        })
    }

    private fun initNewsRequest() {
        newsAdapter = TrackNewAdapter( null)
        recyclerView!!.setAdapter(newsAdapter)

        tracksPresenter = TracksPresenter(this)
        newsAdapter!!.loadingOn()
        tracksPresenter!!.getTracks(keyword, page)
    }

    private fun loadMoreData() {
        if (isMore && !isLoading) {
            newsAdapter!!.loadingOn()
            isLoading = true
            Handler().postDelayed({ tracksPresenter!!.getTracks(keyword, page) }, 5000)
        }
    }

    override fun onSuccessGetTracks(trackList: List<Track>) {
        newsAdapter!!.loadingOff()
        newsAdapter!!.addTrackList(trackList)
        newsAdapter!!.notifyDataSetChanged()

        page++
        isLoading = false

        if (newsAdapter!!.getItemCount() <= 0) {
            newsAdapter!!.addEmptyState()
        }

        if (trackList.size == 0) {
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
