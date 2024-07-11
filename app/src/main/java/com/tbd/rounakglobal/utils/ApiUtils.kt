package com.tbd.rounakglobal.controllers

import com.tbd.rounakglobal.interfaces.APIService


object ApiUtils {
    val BASE_URL = "https://ezsoftapp.in/rounak_global/androidApi/index.php/"

val apiService: APIService
    get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)

}