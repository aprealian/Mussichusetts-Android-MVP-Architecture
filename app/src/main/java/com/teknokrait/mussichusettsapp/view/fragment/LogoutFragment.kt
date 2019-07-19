package com.teknokrait.mussichusettsapp.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.MyPreference
import com.teknokrait.mussichusettsapp.view.activity.MainActivity
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_logout.*

class LogoutFragment : BaseFragment() {

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_logout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClick()
    }

    private fun initOnClick() {
        tvLogOut.setOnClickListener {
            context?.let { it1 -> MyPreference.getInstance(it1).logoutSession(activity as MainActivity) }
        }
    }


}
