package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.model.TrackItem
import com.teknokrait.mussichusettsapp.presenter.TracksPresenter
import com.teknokrait.mussichusettsapp.view.adapter.TrackAdapter
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*
import androidx.core.widget.NestedScrollView
import com.teknokrait.mussichusettsapp.local.RealmManager

class FindFragment : BaseFragment(), TracksPresenter.View {

    private var keyword: String = ""
    private var page = 1
    private var isMore = true
    private var isLoading = false

    private var tracksPresenter: TracksPresenter? = null
    private var newsAdapter: TrackAdapter? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_find
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open();
        initOnClick()
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
//        tvNotFound!!.setVisibility(View.GONE)
//        llError!!.setVisibility(View.GONE)

        newsAdapter = TrackAdapter( null)
        recyclerView!!.setAdapter(newsAdapter)

        tracksPresenter = TracksPresenter(this)
//        newsAdapter!!.addLoadingView()
        tracksPresenter!!.getTracks(keyword, page)
    }

    private fun loadMoreData() {

        Toast.makeText(context, "load more..", Toast.LENGTH_LONG).show()

        if (isMore && !isLoading) {
//            newsAdapter!!.addLoadingView()
            isLoading = true
            Handler().postDelayed({ tracksPresenter!!.getTracks(keyword, page) }, 5000)
        }
    }

    private fun initOnClick() {
//        tvRetry!!.setOnClickListener(View.OnClickListener { initNewsRequest() })
    }

    override fun onSuccessGetTracks(trackList: List<Track>) {

        Toast.makeText(context,trackList.size.toString(), Toast.LENGTH_LONG).show()
//        llError!!.setVisibility(View.GONE)

//        LocalDataStorage.getInstance(context).saveArticles(articleList)
//        newsAdapter!!.removeLoadingView()
        newsAdapter!!.addTracks(trackList)
        newsAdapter!!.notifyDataSetChanged()
//        scrollListener!!.setLoaded()

        page++
        isLoading = false

        if (newsAdapter!!.getItemCount() <= 0) {
//            tvNotFound!!.setVisibility(View.VISIBLE)
//            tvNotFound!!.text = String.format(getString(R.string.news_with_keyword_not_found), keyword)
        }

        if (trackList.size == 0) {
            isMore = false
        }
    }

    override fun onErrorGetTracks(throwable: Throwable) {
        //newsAdapter!!.removeLoadingView()
        if (newsAdapter!!.getItemCount() > 0) {
            //tvNotFound!!.setVisibility(View.GONE)
        } else {
            //llError!!.setVisibility(View.VISIBLE)
        }
    }

    internal fun isEmptyNews() {
        if (newsAdapter!!.getItemCount() <= 0) {
            //tvNotFound!!.setVisibility(View.VISIBLE)
        }
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
