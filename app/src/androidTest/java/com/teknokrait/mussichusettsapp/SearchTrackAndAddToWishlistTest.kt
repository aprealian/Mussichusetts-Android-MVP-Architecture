package com.teknokrait.mussichusettsapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.teknokrait.mussichusettsapp.view.activity.MainActivity
import com.teknokrait.mussichusettsapp.view.adapter.TrackNewAdapter
import com.teknokrait.mussichusettsapp.view.fragment.FindFragment
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Aprilian Nur Wakhid Daini on 7/24/2019.
 */

@RunWith(AndroidJUnit4::class)
class SearchTrackAndAddToWishlistTest {

    private val keyword_artist = "jason"

    @Rule
    @JvmField
    val activityRule  =  ActivityTestRule(MainActivity::class.java)

    @Test
    fun showFindTrackResultAndAddToWishlist() {
        // test to find the track by artist
        Espresso.onView(withId(R.id.etSearch))
            .perform(typeText(keyword_artist))

        //add item to wishlist
        addTrackItemToWishlist()

        //open wishlist fragment
        Espresso.onView(withId(R.id.viewpager)).perform(ViewActions.swipeLeft())

        //remove track from wishlist
        removeTrackItemToWishlist()

        //back to serach result and look at wishlist button state on change
        Espresso.onView(withId(R.id.viewpager)).perform(ViewActions.swipeRight())
    }


    fun addTrackItemToWishlist() {
        // add track item to wishlist,
        // use allOf and isDisplayed to avoid ambigous multiple id recyclerView
        Espresso.onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(
                RecyclerViewActions
                .actionOnItemAtPosition<TrackNewAdapter.TrackViewHolder>(0, MyViewAction.clickChildViewWithId(R.id.ivLike)))

//        //Click list item by position
//        Espresso.onView(allOf(withId(R.id.recyclerView), isDisplayed()))
//            .perform(RecyclerViewActions
//                .actionOnItemAtPosition<TrackNewAdapter.TrackViewHolder>(0, MyViewAction.click()))

    }

    fun removeTrackItemToWishlist() {
        // add track item to wishlist,
        // use allOf and isDisplayed to avoid ambigous multiple id recyclerView
        Espresso.onView(allOf(withId(R.id.recyclerView), isDisplayed()))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<TrackNewAdapter.TrackViewHolder>(0, MyViewAction.clickChildViewWithId(R.id.ivLike)))
    }

    private fun findTrackFragment(): FindFragment {
        val activity = activityRule.activity as MainActivity
        val transaction = activity.getSupportFragmentManager().beginTransaction()
        val findTrackFragment = FindFragment()
        transaction.add(findTrackFragment, "findFragment")
        transaction.commit()
        return findTrackFragment
    }

}
