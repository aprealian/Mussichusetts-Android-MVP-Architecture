package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.presenter.TracksPresenter
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import androidx.core.widget.NestedScrollView
import com.teknokrait.mussichusettsapp.view.adapter.TrackNewAdapter
import com.teknokrait.mussichusettsapp.local.RealmManager
import kotlinx.android.synthetic.main.view_search.*

class FindFragment : BaseFragment(), TracksPresenter.View {

    private var keyword: String = ""
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
        RealmManager.open()
        initTrackRequest(keyword)
        initAdapter()
        initTypingKeyword();
    }

    private fun initTypingKeyword() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0 == null)
                    keyword = ""
                else
                    keyword = p0.toString()
                initTrackRequest(keyword)
            }
        })
    }

    private fun initAdapter() {
        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {

                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {

                    val visibleItemCount = recyclerView.linearLayoutManager!!.childCount
                    val totalItemCount = recyclerView.linearLayoutManager!!.itemCount
                    val pastVisiblesItems = recyclerView.linearLayoutManager!!.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        //after reach latest data then load more data (load next page)
                        loadMoreData(keyword)
                    }
                }
            }
        })
    }

    private fun initTrackRequest(keyword:String) {
        page = 1
        isLoading = false
        isMore = true
        newsAdapter = TrackNewAdapter( null)
        recyclerView!!.adapter = newsAdapter

        tracksPresenter = TracksPresenter(this)
        newsAdapter!!.loadingOn()
        tracksPresenter!!.getTracks(keyword, page)
    }

    private fun loadMoreData(keyword: String) {
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
