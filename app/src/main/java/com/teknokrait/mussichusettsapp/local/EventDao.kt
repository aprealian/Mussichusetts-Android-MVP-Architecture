package com.teknokrait.mussichusettsapp.local

import androidx.annotation.NonNull
import com.teknokrait.mussichusettsapp.model.Event
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import timber.log.Timber
import java.util.*

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

    fun loadByDate(date: Date): Event? {
        return mRealm.where(Event::class.java).equalTo(Event::eventDate.name,date).findFirst()
    }

    fun countEventByDate(date: Date): Int? {

        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val date1 = Date(calendar.timeInMillis)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val date2 = Date(calendar.timeInMillis)

        return mRealm.where(Event::class.java)
            .greaterThanOrEqualTo(Event::eventDate.name, date1)
            .lessThan(Event::eventDate.name, date2)
            .findAll().size

        //return mRealm.where(Event::class.java).equalTo(Event::eventDate.name,date).findAll().count()
    }

    fun loadEventsByDate(date: Date):  List<Event> {

        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val date1 = Date(calendar.timeInMillis)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val date2 = Date(calendar.timeInMillis)

        return mRealm.where(Event::class.java)
            .greaterThanOrEqualTo(Event::eventDate.name, date1)
            .lessThan(Event::eventDate.name, date2)
            .sort(Event::eventDate.name, Sort.ASCENDING)
            .findAll()
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