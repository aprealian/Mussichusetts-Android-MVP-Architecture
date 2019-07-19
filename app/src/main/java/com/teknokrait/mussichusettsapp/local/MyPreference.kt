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

    fun addUserSession(tag: String, user: User) {
        sharedPreferences.edit().putString(tag, gson.toJson(user)).apply()
    }

    fun getUserSession(tag: String): User? {
        val user: User = gson.fromJson<User>(sharedPreferences.getString(tag, ""),
                object : TypeToken<User>() {

                }.type)
        return user
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
        if (getUserSession(TAG_USER) == null){
            activity.startActivity(Intent(activity, SplashScreenActivity::class.java))
        }
    }

}