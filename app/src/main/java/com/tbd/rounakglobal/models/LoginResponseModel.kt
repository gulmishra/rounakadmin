package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginResponseModel {
    @Expose
    @SerializedName("success")
    var success: String ?= null
    @Expose
    @SerializedName("message")
    var message: String ?= null

    @Expose
    @SerializedName("id")
    var id: String ?= null

    @Expose
    @SerializedName("name")
    var name: String ?= null

    @Expose
    @SerializedName("email")
    var email: String ?= null

    @Expose
    @SerializedName("photo")
    var photo: String ?= null


}
