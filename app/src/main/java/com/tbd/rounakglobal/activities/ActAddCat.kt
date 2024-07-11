package com.tbd.rounakglobal.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.kaopiz.kprogresshud.KProgressHUD
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.ApiUtils
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.interfaces.APIService
import com.tbd.rounakglobal.models.CommonResponseModel
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ActAddCat :AppCompatActivity() {
    val REQUEST_CODE = 100
    lateinit var imageString: String
    lateinit var imageName: String
    var ivImageCat : ImageView?= null
    var tvSubmit : TextView?= null
    var etTitle : EditText?= null
    var rlImage : RelativeLayout?= null
    var scsType : SearchableSpinner?= null
    var loader : KProgressHUD?= null
    var ivBack : ImageView?= null
    lateinit var login: LoginSession
    var countrySearchList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_cat)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        imageString = ""
        imageName = ""
        countrySearchList = java.util.ArrayList()
        countrySearchList?.add("Present")
        countrySearchList?.add("Upcoming")
        loader = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(resources.getColor(R.color.black))
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.6f)
        scsType =  findViewById(R.id.scsType)
        tvSubmit =  findViewById(R.id.tvSubmit)
        etTitle =  findViewById(R.id.etTitle)
        rlImage =  findViewById(R.id.rlImage)
        ivImageCat =  findViewById(R.id.ivImageCat)
        ivBack =  findViewById(R.id.ivBack)
        val meterSrNumAdapter: ArrayAdapter<*> =
            ArrayAdapter<String>(this@ActAddCat, R.layout.search_layout, countrySearchList!!)
        scsType?.setAdapter(meterSrNumAdapter)
        scsType?.setTitle("Select Type")
        scsType?.setPositiveButton("OK")

        tvSubmit?.setOnClickListener(View.OnClickListener {
            if (etTitle?.text.toString().trim().equals("")) {
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
            }else if(imageName.equals("")){
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show()
            }else {
                loader?.show()
                addCat()
            }
        })
        ivBack?.setOnClickListener(View.OnClickListener {
            finish()
        })
        rlImage?.setOnClickListener(View.OnClickListener {
            openGalleryForImage()
        })
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            ivImageCat?.setImageURI(data?.data)
            val imageUri: Uri = data?.data!!

            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
            imageString = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
            imageName = "" + System.currentTimeMillis() + ".png"
//            Log.w("BTAG","Image "+imageString)
//            Log.w("BTAG","Image "+imageName)
        }
    }
    fun addCat() {
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("title", etTitle?.text.toString().trim())
        map.addProperty("type", scsType?.selectedItem.toString())
        map.addProperty("img_name", imageName)
        map.addProperty("img", imageString)
        Log.w("BTAGG", "Map " + map)
        mAPIService.addCat(map)
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
                                    this@ActAddCat,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()

                            }
                        } else {
                            loader!!.dismiss()
                            Toast.makeText(
                                this@ActAddCat,
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