package com.tbd.rounakglobal.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.LoginSession

class ActSplash : AppCompatActivity() {
    lateinit var login: LoginSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        if (allPermissionsGranted()) {
            doRegularProcess()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)


        }


    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED

    }
    companion object {

        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }

            }.toTypedArray()
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    finish()
                } else {
                    doRegularProcess()
                }
            }
        }
    }
    fun doRegularProcess() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run () {
                handler.removeCallbacksAndMessages(null)

                if (login.isUserLoggedIn()) {
                    Toast.makeText(this@ActSplash, "Welcome Back \uD83D\uDE4F \n"+ login.getUserDetails()?.get("name"), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ActSplash, ActDashboard::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    val intent = Intent(this@ActSplash, ActLogin::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        handler.postDelayed(runnable,3000)
    }
}