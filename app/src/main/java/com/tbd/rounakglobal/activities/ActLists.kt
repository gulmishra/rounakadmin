package com.tbd.rounakglobal.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.gson.JsonObject
import com.kaopiz.kprogresshud.KProgressHUD
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.adapters.AdpCategories
import com.tbd.rounakglobal.adapters.AdpNews
import com.tbd.rounakglobal.controllers.ApiUtils
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.interfaces.APIService
import com.tbd.rounakglobal.models.CatRespDataModel
import com.tbd.rounakglobal.models.CatRespModel
import com.tbd.rounakglobal.models.CommonResponseModel
import com.tbd.rounakglobal.models.NewsRespDataModel
import com.tbd.rounakglobal.models.NewsRespModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActLists : AppCompatActivity()  {
    lateinit var login: LoginSession
    var flag : String ?= null
    var tvNoData : TextView?= null
    var tvTitle : TextView?= null
    var ivBack : ImageView?= null
    var rvLists : RecyclerView?= null
    var loader : KProgressHUD?= null
    var mSwipeRefreshLayout : SwipeRefreshLayout?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_lists)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)
        tvNoData = findViewById(R.id.tvNoData)
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        rvLists = findViewById(R.id.rvLists)
        flag = intent.getStringExtra("flag")
        loader = KProgressHUD.create(this@ActLists)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(resources.getColor(R.color.black))
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.6f)
//        when (flag) {
//            "PresentCat" -> {
//                tvNoData?.text = "No Present Categories Available!"
//                tvTitle?.text = "Present Categories"
//                loader?.show()
//                getCatData("Present")
//            }
//            "UpcomingCat" -> {
//                tvNoData?.text = "No Upcoming Categories Available!"
//                tvTitle?.text = "Upcoming Categories"
//                loader?.show()
//                getCatData("Upcoming")
//            }
//            "ActCat" -> {
//                tvNoData?.text = "No Active Sub-Categories Available!"
//                tvTitle?.text = "Active Sub-Categories"
//                loader?.show()
//                getSubCat("Active")
//            }
//            "InActCat" -> {
//                tvNoData?.text = "No In-Active Sub-Categories Available!"
//                tvTitle?.text = "In-Active Sub-Categories"
//                loader?.show()
//                getSubCat("Inactive")
//            }
//            "ActNews" -> {
//                tvNoData?.text = "No Active News Available!"
//                tvTitle?.text = "Active News"
//                getNews("Active")
//            }
//            "InActNews" -> {
//                tvNoData?.text = "No In-Active News Available!"
//                tvTitle?.text = "In-Active News"
//                getNews("Inactive")
//            }
//            "ActUsers" -> {
//                tvNoData?.text = "No Active Users Available!"
//                tvTitle?.text = "Active Users"
//            }
//            "InActUsers" -> {
//                tvNoData?.text = "No In-Active Users Available!"
//                tvTitle?.text = "In-Active Users"
//            }
//            else -> {
//                tvNoData?.text = "No Data!"
//                tvTitle?.text = "No Data!"
//            }
//        }

        ivBack?.setOnClickListener(View.OnClickListener {
            finish()
        })

        mSwipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            mSwipeRefreshLayout?.isRefreshing = false
            when (flag) {
                "PresentCat" -> {
                    loader?.show()
                    getCatData("Present")
                }
                "UpcomingCat" -> {
                    loader?.show()
                    getCatData("Upcoming")
                }
                "ActCat" -> {
                    loader?.show()
                    getSubCat("Active")
                }
                "InActCat" -> {
                    loader?.show()
                    getSubCat("Inactive")
                }
                "ActNews" -> {
                    loader?.show()
                    getNews("Active");
                }
                "InActNews" -> {
                    loader?.show()
                    getNews("Inactive")
                }
                "ActUsers" -> {

                }
                "InActUsers" -> {

                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        when (flag) {
            "PresentCat" -> {
                tvNoData?.text = "No Present Categories Available!"
                tvTitle?.text = "Present Categories"
                loader?.show()
                getCatData("Present")
            }
            "UpcomingCat" -> {
                tvNoData?.text = "No Upcoming Categories Available!"
                tvTitle?.text = "Upcoming Categories"
                loader?.show()
                getCatData("Upcoming")
            }
            "ActCat" -> {
                tvNoData?.text = "No Active Sub-Categories Available!"
                tvTitle?.text = "Active Sub-Categories"
                loader?.show()
                getSubCat("Active")
            }
            "InActCat" -> {
                tvNoData?.text = "No In-Active Sub-Categories Available!"
                tvTitle?.text = "In-Active Sub-Categories"
                loader?.show()
                getSubCat("Inactive")
            }
            "ActNews" -> {
                tvNoData?.text = "No Active News Available!"
                tvTitle?.text = "Active News"
                getNews("Active")
            }
            "InActNews" -> {
                tvNoData?.text = "No In-Active News Available!"
                tvTitle?.text = "In-Active News"
                getNews("Inactive")
            }
            "ActUsers" -> {
                tvNoData?.text = "No Active Users Available!"
                tvTitle?.text = "Active Users"
            }
            "InActUsers" -> {
                tvNoData?.text = "No In-Active Users Available!"
                tvTitle?.text = "In-Active Users"
            }
            else -> {
                tvNoData?.text = "No Data!"
                tvTitle?.text = "No Data!"
            }
        }
    }
    fun getCatData(type: String){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("type", type)
        mAPIService.getCat(map)
            .enqueue(object : Callback<CatRespModel> {
                override fun onResponse(call: Call<CatRespModel>, response: Response<CatRespModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                if(response.body()!!.data?.size!! > 0){
                                    tvNoData?.visibility = View.GONE
                                    rvLists?.visibility = View.VISIBLE
                                    val obj_adapter = response.body()!!.data?.let {
                                        AdpCategories(
                                            it,
                                            this@ActLists,
                                            object : AdpCategories.OnClickListener {
                                                override fun OnItemClick(
                                                    slots: CatRespDataModel?,
                                                    position: Int,
                                                    flag: String?
                                                ) {
                                                    if(flag.equals("delete")){
                                                        val dialog = Dialog(this@ActLists)
                                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                        dialog.setContentView(R.layout.dlg_logout)
                                                        dialog.window!!.setLayout(
                                                            WindowManager.LayoutParams.MATCH_PARENT,
                                                            WindowManager.LayoutParams.WRAP_CONTENT
                                                        )
                                                        dialog.setCancelable(false)

                                                        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
                                                        val tvCancelDialog = dialog.findViewById<TextView>(R.id.tvCancelDialog)
                                                        val tvLogout = dialog.findViewById<TextView>(R.id.tvLogout)
                                                        tvLogout?.text = "Delete"
                                                        tvTitle?.text = "Hey "+login.getUserDetails()?.get("name") + " would you really like to delete Category " + slots!!.title +"?"
                                                        tvLogout.setOnClickListener {
                                                            dialog.dismiss()
                                                            loader!!.show()
                                                            deleteCat(slots.title!!, slots.id!!,type)
                                                        }
                                                        tvCancelDialog.setOnClickListener { dialog.dismiss() }
                                                        dialog.show()
                                                    }else if (flag.equals("edit")){
                                                        val intent = Intent(this@ActLists, ActEditCat::class.java)
                                                        intent.putExtra("id", ""+slots!!.id)
                                                        startActivity(intent)
                                                    }

                                                }
                                            }
                                        )
                                    }

                                    rvLists?.adapter = obj_adapter
                                }else{
                                    tvNoData?.visibility = View.VISIBLE
                                    rvLists?.visibility = View.GONE
                                }





                            }
                        }else{
                            loader!!.dismiss()
                            Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                            tvNoData?.visibility = View.VISIBLE
                            rvLists?.visibility = View.GONE
                        }
                    }


                }

                override fun onFailure(call: Call<CatRespModel>, t: Throwable) {
                    loader!!.dismiss()
                    tvNoData?.visibility = View.VISIBLE
                    rvLists?.visibility = View.GONE
                    Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }
    fun getSubCat(type: String){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("type", type)
        mAPIService.getSubCat(map)
            .enqueue(object : Callback<CatRespModel> {
                override fun onResponse(call: Call<CatRespModel>, response: Response<CatRespModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                if(response.body()!!.data?.size!! > 0){
                                    tvNoData?.visibility = View.GONE
                                    rvLists?.visibility = View.VISIBLE
                                    val obj_adapter = response.body()!!.data?.let {
                                        AdpCategories(
                                            it,
                                            this@ActLists,
                                            object : AdpCategories.OnClickListener {
                                                override fun OnItemClick(
                                                    slots: CatRespDataModel?,
                                                    position: Int,
                                                    flag: String?
                                                ) {
                                                    if(flag.equals("delete")){
                                                        val dialog = Dialog(this@ActLists)
                                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                        dialog.setContentView(R.layout.dlg_logout)
                                                        dialog.window!!.setLayout(
                                                            WindowManager.LayoutParams.MATCH_PARENT,
                                                            WindowManager.LayoutParams.WRAP_CONTENT
                                                        )
                                                        dialog.setCancelable(false)

                                                        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
                                                        val tvCancelDialog = dialog.findViewById<TextView>(R.id.tvCancelDialog)
                                                        val tvLogout = dialog.findViewById<TextView>(R.id.tvLogout)
                                                        tvLogout?.text = "Delete"
                                                        tvTitle?.text = "Hey "+login.getUserDetails()?.get("name") + " would you really like to delete Sub Category " + slots!!.title +"?"
                                                        tvLogout.setOnClickListener {
                                                            dialog.dismiss()
                                                            loader!!.show()
                                                            deleteSubCat(slots.title!!, slots.id!!,type)
                                                        }
                                                        tvCancelDialog.setOnClickListener { dialog.dismiss() }
                                                        dialog.show()
                                                    }else if (flag.equals("edit")){
                                                        val intent = Intent(this@ActLists, ActEditSubCat::class.java)
                                                        intent.putExtra("id", ""+slots!!.id)
                                                        startActivity(intent)
                                                    }
                                                }
                                            }
                                        )
                                    }

                                    rvLists?.adapter = obj_adapter
                                }else{
                                    tvNoData?.visibility = View.VISIBLE
                                    rvLists?.visibility = View.GONE
                                }





                            }
                        }else{
                            loader!!.dismiss()
                            Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                            tvNoData?.visibility = View.VISIBLE
                            rvLists?.visibility = View.GONE
                        }
                    }


                }

                override fun onFailure(call: Call<CatRespModel>, t: Throwable) {
                    loader!!.dismiss()
                    tvNoData?.visibility = View.VISIBLE
                    rvLists?.visibility = View.GONE
                    Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }


    fun getNews(type: String){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("count", "1")
        map.addProperty("type", type)
        mAPIService.getNews(map)
            .enqueue(object : Callback<NewsRespModel> {
                override fun onResponse(call: Call<NewsRespModel>, response: Response<NewsRespModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                if(response.body()!!.data?.size!! > 0){
                                    tvNoData?.visibility = View.GONE
                                    rvLists?.visibility = View.VISIBLE
                                    val obj_adapter = response.body()!!.data?.let {
                                        AdpNews(
                                            it,
                                            this@ActLists,
                                            object : AdpNews.OnClickListener {
                                                override fun OnItemClick(
                                                    slots: NewsRespDataModel?,
                                                    position: Int,
                                                    flag: String?
                                                ) {
                                                    if(flag.equals("delete")) {
                                                        val dialog = Dialog(this@ActLists)
                                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                        dialog.setContentView(R.layout.dlg_logout)
                                                        dialog.window!!.setLayout(
                                                            WindowManager.LayoutParams.MATCH_PARENT,
                                                            WindowManager.LayoutParams.WRAP_CONTENT
                                                        )
                                                        dialog.setCancelable(false)

                                                        val tvTitle =
                                                            dialog.findViewById<TextView>(R.id.tvTitle)
                                                        val tvCancelDialog =
                                                            dialog.findViewById<TextView>(R.id.tvCancelDialog)
                                                        val tvLogout =
                                                            dialog.findViewById<TextView>(R.id.tvLogout)
                                                        tvLogout?.text = "Delete"
                                                        tvTitle?.text =
                                                            "Hey " + login.getUserDetails()
                                                                ?.get("name") + " would you really like to delete News " + slots!!.title + "?"
                                                        tvLogout.setOnClickListener {
                                                            dialog.dismiss()
                                                            loader!!.show()
                                                            deleteNews(
                                                                slots.title!!,
                                                                slots.id!!,
                                                                type
                                                            )
                                                        }
                                                        tvCancelDialog.setOnClickListener { dialog.dismiss() }
                                                        dialog.show()
                                                    }else if (flag.equals("edit")){
                                                        val intent = Intent(this@ActLists, ActEditNews::class.java)
                                                        intent.putExtra("id", ""+slots!!.id)
                                                        startActivity(intent)
                                                    }
                                                }
                                            }
                                        )
                                    }

                                    rvLists?.adapter = obj_adapter
                                }else{
                                    tvNoData?.visibility = View.VISIBLE
                                    rvLists?.visibility = View.GONE
                                }





                            }
                        }else{
                            loader!!.dismiss()
                            Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                            tvNoData?.visibility = View.VISIBLE
                            rvLists?.visibility = View.GONE
                        }
                    }


                }

                override fun onFailure(call: Call<NewsRespModel>, t: Throwable) {
                    loader!!.dismiss()
                    tvNoData?.visibility = View.VISIBLE
                    rvLists?.visibility = View.GONE
                    Toast.makeText(this@ActLists, "Please Try Later!", Toast.LENGTH_SHORT).show()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }

    fun deleteCat(title :String,id: String,type: String) {
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("title", title)
        map.addProperty("id",id)
        Log.w("BTAGG", "Map " + map)
        mAPIService.deleteCat(map)
            .enqueue(object : Callback<CommonResponseModel> {
                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    Log.w("BTAGG", "Map " + response)
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                Toast.makeText(
                                    this@ActLists,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loader!!.show()
                                getCatData(type)

                            }
                        } else {
                            loader!!.dismiss()
                            Toast.makeText(
                                this@ActLists,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
                    loader!!.dismiss()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })

    }

    fun deleteSubCat(title :String,id: String,type: String) {
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("title", title)
        map.addProperty("sub_cat_id",id)
        Log.w("BTAGG", "Map " + map)
        mAPIService.deleteSubCat(map)
            .enqueue(object : Callback<CommonResponseModel> {
                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    Log.w("BTAGG", "Map " + response)
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                Toast.makeText(
                                    this@ActLists,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loader!!.show()
                                getSubCat(type)

                            }
                        } else {
                            loader!!.dismiss()
                            Toast.makeText(
                                this@ActLists,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
                    loader!!.dismiss()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })

    }

    fun deleteNews(title :String,id: String,type: String) {
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("title", title)
        map.addProperty("id",id)
        Log.w("BTAGG", "Map " + map)
        mAPIService.deleteNews(map)
            .enqueue(object : Callback<CommonResponseModel> {
                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    Log.w("BTAGG", "Map " + response)
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                Toast.makeText(
                                    this@ActLists,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loader!!.show()
                                getNews(type)

                            }
                        } else {
                            loader!!.dismiss()
                            Toast.makeText(
                                this@ActLists,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
                    loader!!.dismiss()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })

    }

}
