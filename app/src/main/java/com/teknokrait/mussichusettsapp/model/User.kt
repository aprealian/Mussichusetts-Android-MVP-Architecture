package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */

data class User(
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("email")
        @Expose
        val email: String,
        @SerializedName("gender")
        @Expose
        val gender: String,
        @SerializedName("type")
        @Expose
        val type: String)