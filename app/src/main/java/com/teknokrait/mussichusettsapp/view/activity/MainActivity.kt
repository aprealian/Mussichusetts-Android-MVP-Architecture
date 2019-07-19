package com.teknokrait.mussichusettsapp.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.MyPreference
import com.teknokrait.mussichusettsapp.view.adapter.CustomPagerAdapter
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_find.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyPreference.getInstance(applicationContext).checkSession(this)
        initTab()
    }

    private fun initTab() {
        val adapter = CustomPagerAdapter(supportFragmentManager)
        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = adapter

        val colors = resources.getStringArray(R.array.colors)
        val titles = resources.getStringArray(R.array.tab_titles)
        val navigationTabBar = findViewById<View>(R.id.navTabBar) as NavigationTabBar
        val models =  arrayListOf<NavigationTabBar.Model>()
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_action_home_unactive),
                        Color.parseColor(colors[0])
                ).selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_home_active))
                        .title(titles[0])
                        .badgeTitle(titles[0])
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_action_bookmark_unactive),
                        Color.parseColor(colors[1])
                ).selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_bookmark_active))
                        .title(titles[1])
                        .badgeTitle(titles[1])
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_action_menu_unactive),
                        Color.parseColor(colors[2])
                ).selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_menu_active))
                        .title(titles[2])
                        .badgeTitle(titles[2])
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_action_info_unactive),
                        Color.parseColor(colors[3])
                ).selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_info_active))
                        .title(titles[3])
                        .badgeTitle(titles[3])
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_action_info_unactive),
                        Color.parseColor(colors[3])
                ).selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_info_active))
                        .title(titles[3])
                        .badgeTitle(titles[3])
                        .build()
        )
        navigationTabBar.models = models
        navigationTabBar.setViewPager(viewPager, 0)

        navigationTabBar.titleMode = NavigationTabBar.TitleMode.ACTIVE
        navigationTabBar.badgeGravity = NavigationTabBar.BadgeGravity.BOTTOM
        navigationTabBar.badgePosition = NavigationTabBar.BadgePosition.CENTER
    }
}
