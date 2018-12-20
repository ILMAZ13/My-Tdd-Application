package com.flatstack.mytddapplication.entities

import com.google.gson.annotations.SerializedName

open class RespBase {
    @SerializedName("Response")
    var response: Boolean = false
    @SerializedName("Error")
    var error: String? = null
}
