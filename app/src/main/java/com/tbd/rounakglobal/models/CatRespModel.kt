package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CatRespModel {

    @Expose
    @SerializedName("success")
    var success: String ?= null

    @Expose
    @SerializedName("data")
    var data: ArrayList<CatRespDataModel> ?= null
}

class CatRespDataModel{
    @Expose
    @SerializedName("id")
    var id: String ?= null
    @Expose
    @SerializedName("title")
    var title: String ?= null
    @Expose
    @SerializedName("image")
    var image: String ?= null

    constructor(id: String?, title: String?, image: String?) {
        this.id = id
        this.title = title
        this.image = image
    }
}