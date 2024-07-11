package com.tbd.rounakglobal.activities

import android.R.attr.path
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.kaopiz.kprogresshud.KProgressHUD
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.ApiUtils
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.interfaces.APIService
import com.tbd.rounakglobal.models.CatRespDataModel
import com.tbd.rounakglobal.models.CatRespModel
import com.tbd.rounakglobal.models.CommonResponseModel
import com.tbd.rounakglobal.utils.compressVideo
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class ActAddNews : AppCompatActivity() {
    val REQUEST_CODE = 104

   var videoName : String? = ""
   var video : String? = ""
     var imageStringOne: String ? = ""
     var imageNameOne: String ? = ""
     var imageStringTwo: String ? = ""
     var imageNameTwo: String ? = ""
     var imageStringThree: String ? = ""
     var imageNameThree: String ? = ""

    var ivImageNewsOne : ImageView?= null
    var ivImageNewsTwo : ImageView?= null
    var ivImageNewsThree : ImageView?= null
    var ivBack : ImageView?= null
    var ivVideo : ImageView?= null
    var tvSubmit : TextView?= null
    var etTitle : EditText?= null
    var etDescr : EditText?= null
    var cbVideo : CheckBox?= null
    var cbImages : CheckBox?= null
    var cbNoMedia : CheckBox?= null
    var rlImageOne : RelativeLayout?= null
    var rlImageTwo : RelativeLayout?= null
    var rlImageThree : RelativeLayout?= null
    var rlVideo : RelativeLayout?= null
    var scsType : SearchableSpinner?= null
    var scsSubCat : SearchableSpinner?= null
    var tv_Image : TextView?= null
    var llImages : LinearLayout?= null
    var tvVideo : TextView?= null

    var loader : KProgressHUD?= null
    var cat_id : String?= ""
    var sub_cat_id : String?= ""
    lateinit var login: LoginSession
    var CatSearchList: ArrayList<String>? = null
    var CatDataList: ArrayList<CatRespDataModel>? = null
    var SubCatSearchList: ArrayList<String>? = null
    var SubCatDataList: ArrayList<CatRespDataModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_news)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        imageStringOne = ""
        imageNameOne = ""
        imageStringTwo = ""
        imageNameTwo = ""
        imageStringThree = ""
        imageNameThree = ""
        CatSearchList = java.util.ArrayList()
        CatDataList = java.util.ArrayList()


        loader = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(resources.getColor(R.color.black))
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.6f)
        scsType =  findViewById(R.id.scsType)
        scsSubCat =  findViewById(R.id.scsSubCat)
        tvSubmit =  findViewById(R.id.tvSubmit)
        etTitle =  findViewById(R.id.etTitle)
        etDescr =  findViewById(R.id.etDescr)
        ivVideo =  findViewById(R.id.ivVideo)
        ivBack =  findViewById(R.id.ivBack)
        ivImageNewsThree =  findViewById(R.id.ivImageNewsThree)
        ivImageNewsTwo =  findViewById(R.id.ivImageNewsTwo)
        ivImageNewsOne =  findViewById(R.id.ivImageNewsOne)
        rlImageOne =  findViewById(R.id.rlImageOne)
        rlImageTwo =  findViewById(R.id.rlImageTwo)
        rlImageThree =  findViewById(R.id.rlImageThree)
        rlVideo =  findViewById(R.id.rlVideo)
        cbImages =  findViewById(R.id.cbImages)
        cbNoMedia =  findViewById(R.id.cbNoMedia)
        cbVideo =  findViewById(R.id.cbVideo)
        tv_Image =  findViewById(R.id.tv_Image)
        llImages =  findViewById(R.id.llImages)
        tvVideo =  findViewById(R.id.tvVideo)



        tvSubmit?.setOnClickListener(View.OnClickListener {
            if(cat_id.equals("0")){
                Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show()
            }else  if(sub_cat_id.equals("0")){
                Toast.makeText(this, "Please select sub category", Toast.LENGTH_SHORT).show()
            }else if (etTitle?.text.toString().trim().equals("")) {
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
            }else if(etDescr?.text.toString().trim().equals("")) {
                Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show()
            }else if((!cbImages!!.isChecked) && (!cbVideo!!.isChecked) && (!cbNoMedia!!.isChecked) ) {
                Toast.makeText(this, "Please select one among video, image and no media.", Toast.LENGTH_SHORT).show()
            }else if(cbImages!!.isChecked && imageNameOne.equals("") && imageNameTwo.equals("") && imageNameThree.equals("")) {
                Toast.makeText(this, "Please select at least one image.", Toast.LENGTH_SHORT).show()
            }else if(cbVideo!!.isChecked && videoName.equals("")) {
                Toast.makeText(this, "Please select video.", Toast.LENGTH_SHORT).show()
            }else {
                loader?.show()
                addNews()
            }
        })

        rlImageOne?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        })
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "video/*"
//        startActivityForResult(intent, 100)
        rlImageTwo?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 101)
        })

        rlImageThree?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 102)
        })

        rlVideo?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, REQUEST_CODE)
        })

        ivBack?.setOnClickListener(View.OnClickListener {
           finish()
        })
        cbVideo!!.isChecked = true
        tvVideo!!.visibility = View.VISIBLE
        rlVideo!!.visibility = View.VISIBLE
        tv_Image!!.visibility = View.GONE
        llImages!!.visibility = View.GONE

        cbVideo!!.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                cbImages!!.isChecked = false
                cbNoMedia!!.isChecked = false
                tvVideo!!.visibility = View.VISIBLE
                rlVideo!!.visibility = View.VISIBLE
                tv_Image!!.visibility = View.GONE
                llImages!!.visibility = View.GONE
            }/*else{
                cbImages!!.isChecked = true
                tvVideo!!.visibility = View.GONE
                rlVideo!!.visibility = View.GONE
                tv_Image!!.visibility = View.VISIBLE
                llImages!!.visibility = View.VISIBLE
            }*/
        }

        cbImages!!.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                cbVideo!!.isChecked = false
                cbNoMedia!!.isChecked = false
                tvVideo!!.visibility = View.GONE
                rlVideo!!.visibility = View.GONE
                tv_Image!!.visibility = View.VISIBLE
                llImages!!.visibility = View.VISIBLE
            }/*else{
                cbVideo!!.isChecked = true
                tvVideo!!.visibility = View.VISIBLE
                rlVideo!!.visibility = View.VISIBLE
                tv_Image!!.visibility = View.GONE
                llImages!!.visibility = View.GONE
            }*/
        }
        cbNoMedia!!.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                cbVideo!!.isChecked = false
                cbImages!!.isChecked = false
                tvVideo!!.visibility = View.GONE
                rlVideo!!.visibility = View.GONE
                tv_Image!!.visibility = View.GONE
                llImages!!.visibility = View.GONE
            }/*else{
                cbVideo!!.isChecked = true
                tvVideo!!.visibility = View.VISIBLE
                rlVideo!!.visibility = View.VISIBLE
                tv_Image!!.visibility = View.GONE
                llImages!!.visibility = View.GONE
            }*/
        }
        loader?.show()
        getCatData();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {


            val selectedVieo: Uri = data?.data!!
            val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)


            val cursor = contentResolver.query(
                selectedVieo,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()

            val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
            val filePath = cursor!!.getString(columnIndex)
            cursor!!.close()
            Log.w("STAG","Path1 "+filePath)
            try {
                if (filePath != null) {
//                if (outputFilePath != null) {
                    val mp = MediaPlayer.create(this, Uri.parse(filePath))
//                    val mp = MediaPlayer.create(this, Uri.parse(outputFilePath))
                    val duration = mp.duration
                    mp.release()

                    if ((duration / 1000) > 30) {
                            Toast.makeText(this@ActAddNews,"Video greater then 30 sec not allowed",Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                val outputFilePath = filePath.substring(0,filePath.lastIndexOf("/"))+"compressed_video.mp4"

                                compressVideo(selectedVieo, outputFilePath,this@ActAddNews)
                                video = convertVideoToBase64(this, Uri.fromFile(File(outputFilePath)))
//                            video = convertVideoToBase64(this, Uri.parse(outputFilePath))

                                videoName = "" + System.currentTimeMillis() + ".mp4"

                                Glide
                                    .with(this@ActAddNews)
                                    .asBitmap()
                                    .load(Uri.fromFile(File(outputFilePath)))
                                    .into(ivVideo!!)
                            }catch (e : Exception){
                                Log.w("BTAG","Exception "+e.message)
                            }

//                        Glide.with(this@ActAddNews)
//                                .asBitmap()
//                                .load(Uri.fromFile(File(outputFilePath)))
//                                .into(ivVideo!!)

                           // Log.w("STAG","Path "+filePath)
                        }
                       

                }
            } catch (e: Exception) {
                Log.w("STAG","Error "+e)
                e.printStackTrace()
            }
        }else if (resultCode == Activity.RESULT_OK && requestCode == 100){
            ivImageNewsOne?.setImageURI(data?.data)
            val imageUri: Uri = data?.data!!

            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            imageStringOne = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
            imageNameOne = "" + System.currentTimeMillis() + ".png"
        }else if (resultCode == Activity.RESULT_OK && requestCode == 101){
            ivImageNewsTwo?.setImageURI(data?.data)
            val imageUri: Uri = data?.data!!

            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            imageStringTwo = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
            imageNameTwo = "" + System.currentTimeMillis() + ".png"
        }else if (resultCode == Activity.RESULT_OK && requestCode == 102){
            ivImageNewsThree?.setImageURI(data?.data)
            val imageUri: Uri = data?.data!!

            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            imageStringThree = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
            imageNameThree = "" + System.currentTimeMillis() + ".png"
        }
    }

    fun addNews() {

        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService

        try {
            val map = JsonObject()
            map.addProperty("title", etTitle?.text.toString().trim())
            map.addProperty("desc", etDescr?.text.toString().trim())
            map.addProperty("cat_id", cat_id)
            map.addProperty("sub_cat_id", sub_cat_id)

            if(cbVideo!!.isChecked){
                map.addProperty("videoName", videoName)
                map.addProperty("video", video)
                map.addProperty("image_one_name", "")
                map.addProperty("image_one", "")
                map.addProperty("image_two_name", "")
                map.addProperty("image_two", "")
                map.addProperty("image_three_name", "")
                map.addProperty("image_three", "")
            }else if(cbImages!!.isChecked){
                map.addProperty("videoName", "")
                map.addProperty("video", "")
                map.addProperty("image_one_name", imageNameOne)
                map.addProperty("image_one", imageStringOne)
                map.addProperty("image_two_name", imageNameTwo)
                map.addProperty("image_two", imageStringTwo)
                map.addProperty("image_three_name", imageNameThree)
                map.addProperty("image_three", imageStringThree)
            }else if(cbNoMedia!!.isChecked){
                map.addProperty("videoName", "")
                map.addProperty("video", "")
                map.addProperty("image_one_name", "")
                map.addProperty("image_one", "")
                map.addProperty("image_two_name", "")
                map.addProperty("image_two", "")
                map.addProperty("image_three_name", "")
                map.addProperty("image_three", "")
            }


          //  Log.w("BTAGG","Map "+map)
            mAPIService.addNews(map)
                .enqueue(object : Callback<CommonResponseModel> {
                    override fun onResponse(
                        call: Call<CommonResponseModel>,
                        response: Response<CommonResponseModel>
                    ) {
                        loader!!.dismiss()
                        Log.w("BTAGG", "Map " + response)
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                Toast.makeText(
                                    this@ActAddNews,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()

                            }
                        } else {
                            loader!!.dismiss()
                            Toast.makeText(
                                this@ActAddNews,
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
        }catch (e : Exception){
            Log.w("BTAGG","Exception "+e.message)
        }



    }

    fun getCatData(){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("type", "Present")
        mAPIService.getCat(map)
            .enqueue(object : Callback<CatRespModel> {
                override fun onResponse(call: Call<CatRespModel>, response: Response<CatRespModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                CatSearchList = java.util.ArrayList()
                                CatDataList = java.util.ArrayList()
                                CatDataList = response.body()!!.data
                                CatDataList?.add(
                                    0,
                                    CatRespDataModel("0","---Select Category---","")
                                )
                                for (i in CatDataList?.indices!!) {
                                    CatSearchList!!.add(
                                        CatDataList?.get(i)?.title!!
                                    )
                                }
                                val meterSrNumAdapter: ArrayAdapter<*> =
                                    ArrayAdapter<String>(this@ActAddNews, R.layout.search_layout, CatSearchList!!)
                                scsType?.setAdapter(meterSrNumAdapter)
                                scsType?.setTitle("Select Category")
                                scsType?.setPositiveButton("OK")
                                scsType?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position: Int,
                                        id: Long
                                    ) {
                                        cat_id = "" + CatDataList?.get(position)?.id!!
                                        loader?.show()
                                        SubCatSearchList = java.util.ArrayList()
                                        SubCatDataList = java.util.ArrayList()
                                        getSubCatData()
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }
                                })

                            }
                        }else{
                            cat_id = "0"
                            loader!!.dismiss()

                        }
                    }


                }

                override fun onFailure(call: Call<CatRespModel>, t: Throwable) {
                    loader!!.dismiss()

                    cat_id = "0"
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }

    fun getSubCatData(){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("id", cat_id)
        mAPIService.getSubCat(map)
            .enqueue(object : Callback<CatRespModel> {
                override fun onResponse(call: Call<CatRespModel>, response: Response<CatRespModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                SubCatSearchList = java.util.ArrayList()
                                SubCatDataList = java.util.ArrayList()
                                SubCatDataList = response.body()!!.data
                                SubCatDataList?.add(
                                    0,
                                    CatRespDataModel("0","---Select Sub Category---","")
                                )
                                for (i in SubCatDataList?.indices!!) {
                                    SubCatSearchList!!.add(
                                        SubCatDataList?.get(i)?.title!!
                                    )
                                }
                                val meterSrNumAdapter: ArrayAdapter<*> =
                                    ArrayAdapter<String>(this@ActAddNews, R.layout.search_layout, SubCatSearchList!!)
                                scsSubCat?.setAdapter(meterSrNumAdapter)
                                scsSubCat?.setTitle("Select Sub Category")
                                scsSubCat?.setPositiveButton("OK")
                                scsSubCat?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position: Int,
                                        id: Long
                                    ) {
                                        sub_cat_id = "" + SubCatDataList?.get(position)?.id!!
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }
                                })

                            }
                        }else{
                            sub_cat_id = "0"
                            kotlin.run {
                                SubCatSearchList = java.util.ArrayList()
                                SubCatDataList = java.util.ArrayList()

                                SubCatDataList?.add(
                                    0,
                                    CatRespDataModel("0","---Select Sub Category---","")
                                )

                                val meterSrNumAdapter: ArrayAdapter<*> =
                                    ArrayAdapter<String>(this@ActAddNews, R.layout.search_layout, SubCatSearchList!!)
                                scsSubCat?.setAdapter(meterSrNumAdapter)
                                scsSubCat?.setTitle("Select Sub Category")
                                scsSubCat?.setPositiveButton("OK")
                                scsSubCat?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position: Int,
                                        id: Long
                                    ) {
                                        sub_cat_id = ""
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }
                                })

                            }
                            loader!!.dismiss()

                        }
                    }


                }

                override fun onFailure(call: Call<CatRespModel>, t: Throwable) {
                    loader!!.dismiss()
                    sub_cat_id = "0"

                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }
    private fun convertVideoToBase64(context: Context, videoUri: Uri): String? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(videoUri)
        val byteArrayOutputStream = ByteArrayOutputStream()

        inputStream?.use { input ->
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        }

        val videoBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(videoBytes, Base64.NO_WRAP)
    }

}