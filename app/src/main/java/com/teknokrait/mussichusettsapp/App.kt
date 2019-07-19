package com.teknokrait.mussichusettsapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
