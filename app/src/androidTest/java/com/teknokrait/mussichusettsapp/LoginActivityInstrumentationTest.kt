package com.teknokrait.mussichusettsapp

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.teknokrait.mussichusettsapp.view.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Aprilian Nur Wakhid Daini on 7/24/2019.
 */
@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentationTest {

    @Rule
    @JvmField
    public val rule  =  ActivityTestRule(LoginActivity::class.java)

    @Test
    fun login_success(){
        Log.e("@Test","Performing login using Google Account")
        Espresso.onView(withId(R.id.btnGoogleLogin))
            .perform(ViewActions.click())
    }
}