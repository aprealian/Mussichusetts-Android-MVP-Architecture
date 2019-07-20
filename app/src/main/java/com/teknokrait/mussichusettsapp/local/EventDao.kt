package com.teknokrait.mussichusettsapp.local

import androidx.annotation.NonNull
import com.teknokrait.mussichusettsapp.model.Event
import io.realm.Realm
import io.realm.RealmResults
import timber.log.Timber

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class EventDao(@param:NonNull private val mRealm: Realm) {

    fun save(event: Event) {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                //generate primary key by auto incerement
                val key: Int
                val k = realm.where(Event::class.java).max(Event::eventId.name)?.toInt()
                key = if (k != null) k + 1 else 0

                event.eventId = key
                realm.copyToRealmOrUpdate(event)
            }
        })
    }

    fun save(eventList: List<Event>) {
        mRealm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                realm.copyToRealmOrUpdate(eventList)
            }
        })
    }

    fun loadAll(): RealmResults<Event> {
        return mRealm.where(Event::class.java).findAll().sort(Event::eventId.name)
    }

    fun loadByPage(page:Int): MutableList<Event> {
        return mRealm.where(Event::class.java)
            .findAllAsync()
            .sort(Event::eventId.name)
    }

    fun loadAllAsync(): RealmResults<Event> {
        return mRealm.where(Event::class.java).findAllAsync().sort(Event::eventId.name)
    }

    fun loadBy(id: Long): Event? {
        return mRealm.where(Event::class.java).equalTo(Event::eventId.name,id).findFirst()
    }

    fun remove(eventId: Long) {
        // check result
        val results = mRealm.where(Event::class.java)
            .equalTo(Event::eventId.name, eventId)
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
                mRealm.delete(Event::class.java)
            }
        })
    }

    fun count(): Long {
        return mRealm.where(Event::class.java).count()
    }
}