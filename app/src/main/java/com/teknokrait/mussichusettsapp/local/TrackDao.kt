package com.teknokrait.mussichusettsapp.local

import androidx.annotation.NonNull
import com.teknokrait.mussichusettsapp.model.Track
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class TrackDao(@param:NonNull private val mRealm: Realm) {

    fun save(track: Track) {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                realm.copyToRealmOrUpdate(track)
            }
        })
    }

    fun save(trackList: List<Track>) {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                realm.copyToRealmOrUpdate(trackList)
            }
        })
    }

    fun loadAll(): RealmResults<Track> {
        return mRealm.where(Track::class.java).findAll().sort("trackName")
    }

    fun loadAllAsync(): RealmResults<Track> {
        return mRealm.where(Track::class.java).findAllAsync().sort("trackName")
    }

    fun loadBy(id: Long): Track? {
        return mRealm.where(Track::class.java).equalTo("trackId",id).findFirst()
    }

//    private fun isOnWishList(track: Track): Boolean{
//        RealmManager.open()
//        val trackResult = RealmManager.createTrackDao()?.loadBy(track.trackId)
//        return trackResult != null
//    }

    fun remove(@NonNull `object`: RealmObject) {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                `object`.deleteFromRealm()
            }
        })
    }

    fun removeAll() {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                mRealm.delete(Track::class.java)
            }
        })
    }

    fun count(): Long {
        return mRealm.where(Track::class.java).count()
    }
}