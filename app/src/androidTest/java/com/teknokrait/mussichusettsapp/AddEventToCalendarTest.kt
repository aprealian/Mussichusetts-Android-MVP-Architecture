package com.teknokrait.mussichusettsapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.teknokrait.mussichusettsapp.view.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Aprilian Nur Wakhid Daini on 7/24/2019.
 */

@RunWith(AndroidJUnit4::class)
class AddEventToCalendarTest {

    private val event_name = "Test My Event"
    private val event_name_empty = ""

    @Rule
    @JvmField
    val activityRule  =  ActivityTestRule(MainActivity::class.java)

    @Test
    fun addEventToCalendar() {

        //open add event fragment
        Espresso.onView(withId(R.id.viewpager)).perform(ViewPagerActions.scrollToPage(3))

        //check empty state
        Espresso.onView(withId(R.id.tvCreateEvent)).perform(click())

        //add event name
        Espresso.onView(withId(R.id.etEventName))
            .perform(ViewActions.typeText(event_name))

        //open calendar picker
        Espresso.onView(withId(R.id.ivChooseCalendar)).perform(click())
    }



//    @Test
//    fun login_failure() {
//        Log.e("@Test", "Performing empty data")
//        Espresso.onView((withId(R.id.llChooseCalendar)))
//            .perform(ViewActions.typeText(event_name_empty))}
//
//    }


}
