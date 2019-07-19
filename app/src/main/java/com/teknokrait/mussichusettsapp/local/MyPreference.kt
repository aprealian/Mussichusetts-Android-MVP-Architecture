package com.teknokrait.mussichusettsapp.local

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teknokrait.mussichusettsapp.model.User
import com.teknokrait.mussichusettsapp.view.activity.SplashScreenActivity

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */
class MyPreference private constructor(context: Context) {

    private val TAG_USER:String = "TAG_USER";
    private val sharedPreferences: SharedPreferences
    private val gson: Gson

    init {
        sharedPreferences = context.getSharedPreferences("mussichusetts", Context.MODE_PRIVATE)
        gson = Gson()
    }

    fun addUserSession(user: User) {
        sharedPreferences.edit().putString(TAG_USER, gson.toJson(user)).apply()
    }

    fun getUserSession(): User? {
        if (sharedPreferences.getString(TAG_USER, null) != null){
            val user: User = gson.fromJson<User>(sharedPreferences.getString(TAG_USER, ""),
                object : TypeToken<User>() {
                }.type)
            return user
        }
        return null
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {

        private var instance: MyPreference? = null

        fun getInstance(context: Context): MyPreference {
            if (instance == null) {
                instance = MyPreference(context)
            }
            return instance as MyPreference
        }
    }

    fun checkSession(activity: Activity){
        if (getUserSession() == null){
            activity.startActivity(Intent(activity, SplashScreenActivity::class.java))
        }
    }

    fun logoutSession(activity: Activity){
        sharedPreferences.edit().putString(TAG_USER,null).apply()
        activity.startActivity(Intent(activity, SplashScreenActivity::class.java))
    }

}