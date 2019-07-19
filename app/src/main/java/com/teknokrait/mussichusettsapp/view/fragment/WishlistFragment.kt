package com.teknokrait.mussichusettsapp.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.RealmManager
import com.teknokrait.mussichusettsapp.view.base.BaseFragment

class WishlistFragment : BaseFragment() {

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_wishlist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmManager.open()
        initLoadData()
    }

    private fun initLoadData() {
        val tracks = RealmManager.createTrackDao()?.loadAll()
        Toast.makeText(context,"on wiseliest "+tracks?.size.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        RealmManager.close()
    }

}