package com.tbd.rounakglobal.controllers

import android.app.Application
import android.text.Editable

class AppController : Application() {
    val ONESIGNAL_APP_ID = "6f5d6490-364c-44c4-ac02-9d82fd8473a4"

    companion object {
       lateinit var nameUser: String
       lateinit var mobile: String
       lateinit var password: String
       lateinit var passwordConfirm: String
       lateinit var referralCode: String
       lateinit var otp: String

    }

    override fun onCreate() {
        super.onCreate()
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID)
        nameUser = ""
        mobile = ""
        password = ""
        passwordConfirm = ""
        referralCode = ""
        otp = ""
    }
}