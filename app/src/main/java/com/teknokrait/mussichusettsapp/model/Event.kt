package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
open class Event(
    @PrimaryKey
    @SerializedName(EVENT_ID)
    @Expose
    var eventId: Long,

    @SerializedName(EVENT_NAME)
    @Expose
    var trackName: String?,

    @SerializedName(EVENT_DATE)
    @Expose
    var albumId: Long,

    @Ignore
    var itemType: Int) : RealmObject(){
    constructor() : this(0,"",0, 0)
    companion object {
        const val EVENT_ID = "event_id"
        const val EVENT_NAME = "event_name"
        const val EVENT_DATE = "event_date"
    }
}