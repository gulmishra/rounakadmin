package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonResponseModel {
    @Expose
    @SerializedName("success")
    var success: String ?= null

    @Expose
    @SerializedName("message")
    var message: String ?= null
}