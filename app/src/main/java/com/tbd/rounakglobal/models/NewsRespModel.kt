package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsRespModel {

    @Expose
    @SerializedName("success")
    var success: String ?= null

    @Expose
    @SerializedName("data")
    var data: ArrayList<NewsRespDataModel> ?= null
}

class NewsRespDataModel{
    @Expose
    @SerializedName("id")
    var id: String ?= null
    @Expose
    @SerializedName("title")
    var title: String ?= null

    @Expose
    @SerializedName("description")
    var description: String ?= null

    @Expose
    @SerializedName("video")
    var video: String ?= null

    @Expose
    @SerializedName("image_one")
    var image_one: String ?= null

    @Expose
    @SerializedName("image_two")
    var image_two: String ?= null

    @Expose
    @SerializedName("image_three")
    var image_three: String ?= null

    @Expose
    @SerializedName("created")
    var created: String ?= null

}