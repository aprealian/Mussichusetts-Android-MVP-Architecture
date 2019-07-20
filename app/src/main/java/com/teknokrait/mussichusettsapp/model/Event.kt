package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
open class Event(
    @PrimaryKey
    @SerializedName(EVENT_ID)
    @Expose
    var eventId: Int,

    @SerializedName(EVENT_NAME)
    @Expose
    var eventName: String?,

    @SerializedName(EVENT_DATE)
    @Expose
    var eventDate: Date,

    @Ignore
    var itemType: Int) : RealmObject(){
    constructor() : this(0,"",Calendar.getInstance().getTime(), 0)
    constructor(eventName: String?, eventDate: Date) : this(){
        this.eventName = eventName
        this.eventDate = eventDate
    }
    companion object {
        const val EVENT_ID = "event_id"
        const val EVENT_NAME = "event_name"
        const val EVENT_DATE = "event_date"
    }
}