package com.teknokrait.mussichusettsapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.teknokrait.mussichusettsapp.view.fragment.*

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */
class CustomPagerAdapter  internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 5

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FindFragment()
            1 -> fragment = WishlistFragment()
            2 -> fragment = CalendarFragment()
            3 -> fragment = CreateFragment()
            4 -> fragment = LogoutFragment()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }
}
