package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */

open class Track(
        @PrimaryKey
        @SerializedName("track_id")
        @Expose
        var trackId: Long,

        @SerializedName("track_name")
        @Expose
        var trackName: String?,

        @SerializedName("album_id")
        @Expose
        var albumId: Long,

        @SerializedName("album_name")
        @Expose
        var albumName: String?,

        @SerializedName("artist_id")
        @Expose
        var artistId: Long,

        @SerializedName("artist_name")
        @Expose
        var artistName: String?) : RealmObject(){
                constructor() : this(0,"",0,"",0,"")
        }