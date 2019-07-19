package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */
data class TrackTwo(
        @SerializedName("track_id")
        @Expose
        val trackId: String,
        @SerializedName("track_name")
        @Expose
        val trackName: String,
        @SerializedName("album_id")
        @Expose
        val albumId: String,
        @SerializedName("album_name")
        @Expose
        val albumName: String,
        @SerializedName("artist_id")
        @Expose
        val artistId: String,
        @SerializedName("artist_name")
        @Expose
        val artistName: String)
