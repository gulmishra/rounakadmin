package com.tbd.rounakglobal.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.controllers.LoginSession
import java.text.SimpleDateFormat


class FragDrawer : Fragment() {

    private var drawerListener: FragmentDrawerListener? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var containerView: View? = null
    private var vProfile: View? = null
    var llLogout : LinearLayout ?= null
    var tvName : TextView ?= null
    var tvDate : TextView ?= null
    var tvMobile : TextView ?= null
    var ivProfileImage : ImageView ?= null
    lateinit var login: LoginSession
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    public interface FragmentDrawerListener{
        fun onDrawerItemSelected(flag: String?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kotlin.run {
            login = LoginSession()
            login.LoginSession(requireContext())
        }
        tvMobile = view.findViewById(R.id.tvMobile)
        tvName = view.findViewById(R.id.tvName)
        tvDate = view.findViewById(R.id.tvDate)
        vProfile = view.findViewById(R.id.vProfile)
        llLogout = view.findViewById(R.id.llLogout)
        ivProfileImage = view.findViewById(R.id.ivProfileImage)
        if(!login.getUserDetails()?.get("img").equals("") && (login.getUserDetails()?.get("img")?.toLowerCase()?.startsWith("https") == true || login.getUserDetails()?.get("img")?.toLowerCase()?.startsWith("http")  == true )){
         //   ivProfileImage?.let { DownloadImageFromInternet(it).execute(login.getUserDetails()?.get("img")) }
            Glide.with(requireContext()).load(login.getUserDetails()?.get("img")).error(requireContext().resources.getDrawable(R.drawable.ic_user)).into(ivProfileImage!!)

        }
        vProfile?.setOnClickListener(View.OnClickListener {
            Log.w("BTAG","item profileprofile")
            drawerListener?.onDrawerItemSelected("profile")
        })
        llLogout?.setOnClickListener(View.OnClickListener {
            Log.w("BTAG","item llSettings")
            drawerListener?.onDrawerItemSelected("logout")
        })

        // getInfosTrafic(view)
    }

    override fun onResume() {
        tvName?.text = login.getUserDetails()?.get("name")
        tvMobile?.text = login.getUserDetails()?.get("email")
        val date = System.currentTimeMillis()
        val sdf = SimpleDateFormat("dd MMM yyyy")
        val dateString: String = sdf.format(date)
        tvDate?.text = dateString
        super.onResume()
    }

    fun setDrawerListener(listener: FragmentDrawerListener?) {
        drawerListener = listener
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri?)
    }


    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        Log.w("TAG", "FRAGMENT DRAWER SETUP CALLED")
        containerView = requireActivity().findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        mDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity!!.invalidateOptionsMenu()
                // getShowButton();
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity!!.invalidateOptionsMenu()
                //                for (int i = 0; i < adpNavCstmExpandable.getGroupCount(); i++) {
//                    if (explvFrgDrawer.isGroupExpanded(i)) {
//                        explvFrgDrawer.collapseGroup(i);
//                    }
//                }
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }
        mDrawerLayout!!.setDrawerListener(mDrawerToggle)
        mDrawerLayout!!.post { (mDrawerToggle as ActionBarDrawerToggle).syncState() }
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}