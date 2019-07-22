package com.teknokrait.mussichusettsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Aprilian Nur Wakhid Daini on 7/18/2019.
 */

data class User(
        @SerializedName("id")
        @Expose
        var id: String?,
        @SerializedName("name")
        @Expose
        var name: String?,
        @SerializedName("email")
        @Expose
        var email: String?,
        @SerializedName("photo")
        @Expose
        var photo: String?,
        @SerializedName("gender")
        @Expose
        var gender: String?,
        @SerializedName("type")
        @Expose
        var type: String?) {
        constructor() : this("", "", "", "", "", "")
        constructor(id: String?, name: String?, email: String?, photo: String?, type: String?) : this() {
                this.id = id
                this.name = name
                this.email = email
                this.photo = photo
                this.type = type
        }
}