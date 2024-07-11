package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CatDetailsResponseModel {
    @Expose
    @SerializedName("success")
    var success: String ?= null
    @Expose
    @SerializedName("message")
    var message: String ?= null

    @Expose
    @SerializedName("data")
    var data: CatDetailsDataModel ?= null
}

class CatDetailsDataModel{
    @Expose
    @SerializedName("id")
    var id: String ?= null
    @Expose
    @SerializedName("title")
    var title: String ?= null
    @Expose
    @SerializedName("image")
    var image: String ?= null
    @Expose
    @SerializedName("type")
    var type: String ?= null


}