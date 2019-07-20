package com.teknokrait.mussichusettsapp.local

import com.teknokrait.mussichusettsapp.model.Event
import com.teknokrait.mussichusettsapp.model.Track
import io.realm.Realm

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */

object RealmManager {

    private var mRealm: Realm? = null

    fun open(): Realm? {
        mRealm = Realm.getDefaultInstance()
        return mRealm
    }

    fun close() {
        if (mRealm != null) {
            mRealm!!.close()
        }
    }

    fun createTrackDao(): TrackDao? {
        checkForOpenRealm()
        return mRealm?.let { TrackDao(it) }
    }

    fun createEventDao(): EventDao? {
        checkForOpenRealm()
        return mRealm?.let { EventDao(it) }
    }

    fun clear() {
        checkForOpenRealm()
        mRealm!!.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                realm.delete(Track::class.java)
                realm.delete(Event::class.java)
                //clear rest of your dao classes
            }
        })
    }

    private fun checkForOpenRealm() {
        if (mRealm == null || mRealm!!.isClosed()) {
            throw IllegalStateException("RealmManager: Realm is closed, call open() method first")
        }
    }

}