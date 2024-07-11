package com.tbd.rounakglobal.activities



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.kaopiz.kprogresshud.KProgressHUD
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.ApiUtils
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.interfaces.APIService
import com.tbd.rounakglobal.models.LoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class ActLogin : AppCompatActivity() {

    var tvLogin : TextView ?= null
    var etpassword : EditText ?= null
    var etEmail : EditText ?= null
    fun String.isEmailValid() =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(this).matches()
    var loader : KProgressHUD?= null
    lateinit var login: LoginSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        loader = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setWindowColor(resources.getColor(R.color.black))
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.6f)

        tvLogin = findViewById(R.id.tvLogin)
        etEmail = findViewById(R.id.etEmail)
        etpassword = findViewById(R.id.etpassword)

        tvLogin?.setOnClickListener(View.OnClickListener {
            if (etEmail?.text.toString().trim().equals("")) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            } else if (!etEmail?.text.toString().trim().isEmailValid()) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
            } else if (etpassword?.text.toString().trim().equals("")) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            } else if (etpassword?.text.toString().trim().length < 4) {
                Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show()
            } else {
                loader?.show()
                doLogin()
            }
        })

    }
    fun doLogin(){
        var mAPIService: APIService? = null
        mAPIService = ApiUtils.apiService
        val map = JsonObject()
        map.addProperty("email", etEmail?.text?.toString()?.trim())
        map.addProperty("password", etpassword?.text?.toString()?.trim())
        Log.w("BTAG","Map "+map)
        mAPIService.loginPost(map)
            .enqueue(object : Callback<LoginResponseModel> {
                override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success.equals("1")) {
                            loader!!.dismiss()
                            kotlin.run {
                                Toast.makeText(this@ActLogin, "Welcome To "+resources.getString(R.string.app_name)+" \uD83D\uDE4F \n"+ response.body()!!.name, Toast.LENGTH_SHORT).show()
                                  login.createUserLoginSession(response.body()!!)
                                val intent = Intent(this@ActLogin, ActDashboard::class.java)
                                startActivity(intent)
                                finish()

                            }
                        }else{
                            loader!!.dismiss()
                            Toast.makeText(this@ActLogin, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }


                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    loader!!.dismiss()
                    Toast.makeText(this@ActLogin, "Try Again Later.", Toast.LENGTH_SHORT).show()
                    Log.w("BTAGG", "post registration to API f " + t.message.toString())
                }
            })
    }
}