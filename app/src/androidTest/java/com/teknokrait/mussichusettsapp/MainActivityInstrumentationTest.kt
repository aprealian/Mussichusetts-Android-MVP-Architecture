package com.teknokrait.mussichusettsapp

import android.util.Log
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
class MainActivityInstrumentationTest {

    @Rule
    @JvmField
    public val rule  = ActivityTestRule(MainActivity::class.java)

    private val username_tobe_typed="Ajesh"
    private val correct_password ="password"
    private val wrong_password = "passme123"

    @Test
    fun login_success(){
        Log.e("@Test","Performing login success test")
//        Espresso.onView((withId(R.id.user_name)))
//            .perform(ViewActions.typeText(username_tobe_typed))
//
//        Espresso.onView(withId(R.id.password))
//            .perform(ViewActions.typeText(correct_password))
//
//        Espresso.onView(withId(R.id.login_button))
//            .perform(ViewActions.click())
//
//        Espresso.onView(withId(R.id.login_result))
//            .check(matches(withText(R.string.login_success)))
    }

    @Test
    fun login_failure(){
        Log.e("@Test","Performing login failure test")
//        Espresso.onView((withId(R.id.user_name)))
//            .perform(ViewActions.typeText(username_tobe_typed))
//
//        Espresso.onView(withId(R.id.password))
//            .perform(ViewActions.typeText(wrong_password))
//
//        Espresso.onView(withId(R.id.login_button))
//            .perform(ViewActions.click())
//
//        Espresso.onView(withId(R.id.login_result))
//            .check(matches(withText(R.string.login_failed)))
    }
}