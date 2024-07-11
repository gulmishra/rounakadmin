package com.tbd.rounakglobal.controllers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.tbd.rounakglobal.activities.ActLogin
import com.tbd.rounakglobal.models.LoginResponseModel


class LoginSession {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context
    var PRIVATE_MODE = 0

    private val PREF_NAME = "ROUGLBGUL708"

    val KEY_ID = "id"
    val KEY_NAME = "name"
    val KEY_EMAIL = "email"
    val KEY_ProfileImage = "img"
    val IS_USER_LOGIN = "IsUserLoggedIn";


    fun LoginSession(contexts: Context) {
        sharedPreferences = contexts.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
        context = contexts
    }

    fun createUserLoginSession(loginResponseModel: LoginResponseModel) {
        editor.putBoolean(IS_USER_LOGIN, true)
        editor.putString(KEY_ID, loginResponseModel.id)
        editor.putString(KEY_NAME, loginResponseModel.name)
        editor.putString(KEY_EMAIL, loginResponseModel.email)
        editor.putString(KEY_ProfileImage, loginResponseModel.photo)
        editor.apply()
        editor.commit()
    }



    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false)
    }

    fun getUserDetails(): HashMap<String, String?>? {
        val user = HashMap<String, String?>()
        user[KEY_ID] = sharedPreferences.getString(KEY_ID, "")
        user[KEY_NAME] = sharedPreferences.getString(KEY_NAME, "")
        user[KEY_EMAIL] = sharedPreferences.getString(KEY_EMAIL, "")
        user[KEY_ProfileImage] = sharedPreferences.getString(KEY_ProfileImage, "")
        return user
    }


    fun logout() {
        editor.clear()
        editor.commit()
        val intent = Intent(context, ActLogin::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}