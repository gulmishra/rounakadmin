package com.tbd.rounakglobal.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.activities.ActDashboard
import com.tbd.rounakglobal.activities.ActLogin
import com.tbd.rounakglobal.models.CatRespDataModel
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


/**
 * Created by user on 5/24/2018.
 */
class AdpCategories(
    private val arrayList: ArrayList<CatRespDataModel>,
    private val context: Context,
    private val lisner: OnClickListener

) :
    RecyclerView.Adapter<AdpCategories.ChildHolder>() {
    var driverList: ArrayList<CatRespDataModel>
    var driverListNew: ArrayList<CatRespDataModel>
    var mContext: Context
    var mListener: OnClickListener? = null

   public interface OnClickListener {
        fun OnItemClick(
            slots: CatRespDataModel?,
            position: Int,
            flag: String?
        )
    }


    fun addItem(datum: CatRespDataModel) {
        driverList.add(datum)
        notifyItemInserted(driverList.size)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adp_categories, parent, false)
        return ChildHolder(view)
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val spinner: CatRespDataModel = driverList[position]
        holder.tvTitle.text = spinner.title
        if (!spinner.image.equals("") && (spinner.image?.toLowerCase()?.startsWith("https") == true || spinner.image?.toLowerCase()?.startsWith("http")  == true )){
          //  DownloadImageFromInternet(holder.civImage).execute(spinner.image)
            Glide.with(context).load(spinner.image).into(holder.civImage)
        }

        holder.ivDelete?.setOnClickListener(View.OnClickListener {
           mListener!!.OnItemClick(spinner,position,"delete")
        })

        holder.ivEdit?.setOnClickListener(View.OnClickListener {
           mListener!!.OnItemClick(spinner,position,"edit")
        })
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run () {
                handler.removeCallbacksAndMessages(null)
                holder.pbLoad.visibility = View.GONE
            }
        }

        handler.postDelayed(runnable,1500)

    }

    override fun getItemCount(): Int {
        return driverList.size
    }


    inner class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var civImage: CircleImageView
        var pbLoad: ProgressBar
        var ivDelete: ImageView
        var ivEdit: ImageView


        init {
            tvTitle=itemView.findViewById(R.id.tvTitle)
            civImage=itemView.findViewById(R.id.civImage)
            pbLoad=itemView.findViewById(R.id.pbLoad)
            ivDelete=itemView.findViewById(R.id.ivDelete)
            ivEdit=itemView.findViewById(R.id.ivEdit)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    init {
        this.driverList = arrayList
        driverListNew = arrayList
        this.mContext = context
        this.mListener=lisner

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