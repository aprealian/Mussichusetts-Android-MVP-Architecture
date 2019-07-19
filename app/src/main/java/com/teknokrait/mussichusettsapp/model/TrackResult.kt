package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */


data class RequestResult(
        @SerializedName("message")
        @Expose
        val message: Message
)

data class Message(
        @SerializedName("header")
        @Expose
        val header: Header,
        @SerializedName("body")
        @Expose
        val body: Body
)


data class Header(
        @SerializedName("status_code")
        @Expose
        val status: Int,
        @SerializedName("execute_time")
        @Expose
        val exectueTime: Double,
        @SerializedName("available")
        @Expose
        val avialble: Int)

data class Body(
        @SerializedName("track_list")
        @Expose
        val tracks: List<TrackItem>? = null)

data class TrackItem(
        @SerializedName("track")
        @Expose
        val track: Track)
