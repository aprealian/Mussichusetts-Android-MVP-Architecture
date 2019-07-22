package com.teknokrait.mussichusettsapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import com.google.android.gms.auth.api.signin.GoogleSignInOptions



/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
            .name("mussichusettsapp.db")
            .schemaVersion(2)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()

        // Build a GoogleSignInClient with the options specified by gso.
//        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //calendar event
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

