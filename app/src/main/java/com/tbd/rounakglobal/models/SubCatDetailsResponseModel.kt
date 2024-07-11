package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubCatDetailsResponseModel {
    @Expose
    @SerializedName("success")
    var success: String ?= null
    @Expose
    @SerializedName("message")
    var message: String ?= null
    @Expose
    @SerializedName("data")
    var data: SubCatDetailsDataModel ?= null
}

class SubCatDetailsDataModel{
    @Expose
    @SerializedName("id")
    var id: String ?= null
    @Expose
    @SerializedName("cat_id")
    var cat_id: String ?= null
    @Expose
    @SerializedName("title")
    var title: String ?= null
    @Expose
    @SerializedName("image")
    var image: String ?= null
    @Expose
    @SerializedName("cat_name")
    var cat_name: String ?= null
    @Expose
    @SerializedName("type")
    var type: String ?= null


}