package com.tbd.rounakglobal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardCountResponseModel {
    @Expose
    @SerializedName("success")
    var success: String ?= null
    @Expose
    @SerializedName("presentCat")
    var presentCat: Int ?= null
    @Expose
    @SerializedName("UpcomingCat")
    var UpcomingCat: Int ?= null
    @Expose
    @SerializedName("InActSubCat")
    var InActSubCat: Int ?= null
    @Expose
    @SerializedName("ActSubCat")
    var ActSubCat: Int ?= null
    @Expose
    @SerializedName("ActNews")
    var ActNews: Int ?= null
    @Expose
    @SerializedName("InActNews")
    var InActNews: Int ?= null
    @Expose
    @SerializedName("ActUsers")
    var ActUsers: Int ?= null
    @Expose
    @SerializedName("InActUsers")
    var InActUsers: Int ?= null
    @Expose
    @SerializedName("message")
    var message: String ?= null
}