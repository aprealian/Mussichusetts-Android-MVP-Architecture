package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */

data class User(
        @SerializedName("name")
        @Expose
        val trackId: String,
        @SerializedName("email")
        @Expose
        val trackName: String,
        @SerializedName("gender")
        @Expose
        val albumId: String)