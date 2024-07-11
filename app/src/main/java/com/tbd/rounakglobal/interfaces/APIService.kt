package com.tbd.rounakglobal.interfaces

import com.google.gson.JsonObject
import com.tbd.rounakglobal.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/login")
    fun loginPost(@Body params: JsonObject): Call<LoginResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @GET("AdminApp/getDashCounts")
    fun getDashBoardCount(): Call<DashboardCountResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getCat")
    fun getCat(@Body params: JsonObject): Call<CatRespModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getSubCat")
    fun getSubCat(@Body params: JsonObject): Call<CatRespModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getNews")
    fun getNews(@Body params: JsonObject): Call<NewsRespModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/addCat")
    fun addCat(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/updateCat")
    fun updateCat(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/addSubCat")
    fun addSubCat(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/uploadVideo")
    fun addNews(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/deleteCat")
    fun deleteCat(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/deleteSubCat")
    fun deleteSubCat(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/deleteNews")
    fun deleteNews(@Body params: JsonObject): Call<CommonResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getCatDetails")
    fun getCatDetails(@Body params: JsonObject): Call<CatDetailsResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getSubCatDetails")
    fun getSubCatDetails(@Body params: JsonObject): Call<SubCatDetailsResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @POST("AdminApp/getNewsDetails")
    fun getNewsDetails(@Body params: JsonObject): Call<NewsDetailsResponseModel>

    @Headers("Authorization:  Basic ZXpzb2Z0Ukc6ZXokMTIzUkc=;")
    @Multipart @POST("VideoUpload/uploadVideo")
    fun uploadVideo(@Part video: MultipartBody.Part): Call<Void>
}
