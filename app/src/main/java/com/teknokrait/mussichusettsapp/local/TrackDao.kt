package com.teknokrait.mussichusettsapp.local

import androidx.annotation.NonNull
import com.teknokrait.mussichusettsapp.model.Track
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.RealmObject.deleteFromRealm
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import timber.log.Timber


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

    fun remove(trackId: Long) {
        // check result
        val results = mRealm.where(Track::class.java)
            .equalTo("trackId", trackId)
            .findAll()

        // if result valid, then remove or delete from Realm,
        // to avoid unmanaged Realm object
        if (results.isValid()) {
            mRealm.executeTransaction(Realm.Transaction {
                results.deleteAllFromRealm()
                Timber.d(this.javaClass.name, "execute: items deleted")
            })
        }
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