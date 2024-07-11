package com.tbd.rounakglobal.activities

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.LoginSession
import com.tbd.rounakglobal.fragments.FragDashBoard
import com.tbd.rounakglobal.fragments.FragDrawer

class ActDashboard : AppCompatActivity() , FragDrawer.FragmentDrawerListener{

    private var drawerFragment: FragDrawer? = null
    private var toolbar: Toolbar ?= null

    private var llDashboard: LinearLayout ?= null
    private var llConsultncy: LinearLayout ?= null
    lateinit var login: LoginSession

    private var ivConsultancy: ImageView ?= null
    private var ivDashboard: ImageView ?= null
    private var tvDashboard: TextView ?= null
    private var tvConsultancy: TextView ?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dashboard)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(this)
        }
        toolbar = findViewById(R.id.toolbar)
        llDashboard = findViewById(R.id.llDashboard)
        llConsultncy = findViewById(R.id.llConsultncy)
        ivConsultancy = findViewById(R.id.ivConsultancy)
        ivDashboard = findViewById(R.id.ivDashboard)
        tvDashboard = findViewById(R.id.tvDashboard)
        tvConsultancy = findViewById(R.id.tvConsultancy)



        drawerFragment = getSupportFragmentManager().findFragmentById(R.id.frgActCstmNav) as FragDrawer?
        drawerFragment!!.setUp(
            R.id.frgActCstmNav,
            (findViewById<View>(R.id.drawer_layout) as DrawerLayout)!!, toolbar!!
        )
        drawerFragment!!.setDrawerListener(this)

        llDashboard = findViewById(R.id.llDashboard)


        llDashboard?.setOnClickListener(View.OnClickListener {
            ivDashboard?.setTint(R.color.color_primary)
            tvDashboard?.setTextColor(ContextCompat.getColor(applicationContext, R.color.color_primary))

            tvConsultancy?.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorMoreOffWhite))
            ivConsultancy?.setTint(R.color.colorMoreOffWhite)



            supportFragmentManager.beginTransaction().replace(R.id.flHome, FragDashBoard(), "dashboard").commit()



        })




        llConsultncy?.setOnClickListener(View.OnClickListener {
            ivConsultancy?.setTint(R.color.color_primary)
            tvConsultancy?.setTextColor(ContextCompat.getColor(applicationContext, R.color.color_primary))

            tvDashboard?.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorMoreOffWhite))
            ivDashboard?.setTint(R.color.colorMoreOffWhite)



          //  supportFragmentManager.beginTransaction().replace(R.id.flHome, FragConsultancy(), "consultancy").commit()

        })





        supportFragmentManager.beginTransaction().replace(R.id.flHome, FragDashBoard(), "dashboard").commit()
    }

    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
    }

    override fun onDrawerItemSelected(flag: String?) {
        Log.w("BTAG","item "+flag)
        if (flag.equals("profile")){
//            val intent = Intent(this@ActDashboard, ActProfile::class.java)
//            intent.putExtra("keyIdentifier", "value")
//            startActivity(intent)

        }else if (flag.equals("logout")){
            val dialog = Dialog(this@ActDashboard)
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
            tvTitle?.text = "Hey "+login.getUserDetails()?.get("name") + " would you really like to logout from the app?"
            tvLogout.setOnClickListener {
                dialog.dismiss()
                login.logout()
            }
            tvCancelDialog.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }else if (flag.equals("fav")){
//            val intent = Intent(this@ActDashboard, ActFavourites::class.java)
//            intent.putExtra("keyIdentifier", "value")
//            startActivity(intent)
        }
    }
}