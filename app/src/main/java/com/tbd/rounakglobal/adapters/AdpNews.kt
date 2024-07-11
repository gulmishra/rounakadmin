package com.tbd.rounakglobal.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.smarteist.autoimageslider.SliderView
import com.tbd.rounakglobal.R
import com.tbd.rounakglobal.models.NewsRespDataModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Created by user on 5/24/2018.
 */
class AdpNews(
    private val arrayList: ArrayList<NewsRespDataModel>,
    private val context: Context,
    private val lisner: OnClickListener

) :
    RecyclerView.Adapter<AdpNews.ChildHolder>() {
    var driverList: ArrayList<NewsRespDataModel>
    var driverListNew: ArrayList<NewsRespDataModel>
    var mContext: Context
    var mListener: OnClickListener? = null

   public interface OnClickListener {
        fun OnItemClick(
            slots: NewsRespDataModel?,
            position: Int,
            flag: String?
        )
    }


    fun addItem(datum: NewsRespDataModel) {
        driverList.add(datum)
        notifyItemInserted(driverList.size)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adp_news, parent, false)
        return ChildHolder(view)
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val spinner: NewsRespDataModel = driverList[position]
        val obj_adapter = SliderAdapterExample(context)
        if(!spinner.image_one!!.isEmpty()){
            obj_adapter.addItem(spinner.image_one!!)
        }
        if(!spinner.image_two!!.isEmpty()){
            obj_adapter.addItem(spinner.image_two!!)
        }
        if(!spinner.image_three!!.isEmpty()){
            obj_adapter.addItem(spinner.image_three!!)
        }
        holder.imageSlider.setSliderAdapter(obj_adapter)

        if(spinner.image_one!!.isEmpty() && spinner.image_two!!.isEmpty() && spinner.image_three!!.isEmpty()){
            holder.imageSlider.visibility = View.GONE
        }else{
            holder.imageSlider.visibility = View.VISIBLE
        }
        holder.ivDelete?.setOnClickListener(View.OnClickListener {
            mListener!!.OnItemClick(spinner,position,"delete")
        })
        holder.ivEdit?.setOnClickListener(View.OnClickListener {
            mListener!!.OnItemClick(spinner,position,"edit")
        })
        holder.tvTiltle.text = spinner.title!!.capitalize()
        holder.tvDescription.text = spinner.description!!.capitalize()


        try {
            val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date: Date = format.parse(spinner.created)
            System.out.println(date)
            val sdf: SimpleDateFormat = SimpleDateFormat("yyyy MM dd")
            val sdf1: SimpleDateFormat = SimpleDateFormat("HH:mm a")

            val date1: String = sdf.format(date)
            val time: String = sdf1.format(date)
            holder.tvDate.text = date1 + " " +time
            Log.e("check_date_time", "$date1==$time")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if(spinner.video!!.isEmpty()){
            holder.flVideo.visibility = View.GONE
        }else{
            holder.flVideo.visibility = View.VISIBLE
             val exoPlayer = ExoPlayer.Builder(context).build()
                holder.playerView.player = exoPlayer

            val mediaItem: MediaItem = MediaItem.fromUri(spinner.video!!)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
           exoPlayer.play()
        }






//        holder.tvTitle.text = spinner.title
//        if (!spinner.image.equals("") && (spinner.image?.toLowerCase()?.startsWith("https") == true || spinner.image?.toLowerCase()?.startsWith("http")  == true )){
//          //  DownloadImageFromInternet(holder.civImage).execute(spinner.image)
//            Glide.with(context).load(spinner.image).into(holder.civImage)
//        }
//        val handler = Handler()
//        val runnable = object : Runnable {
//            override fun run () {
//                handler.removeCallbacksAndMessages(null)
//                holder.pbLoad.visibility = View.GONE
//            }
//        }
//
//        handler.postDelayed(runnable,1500)

    }

    override fun getItemCount(): Int {
        return driverList.size
    }


    inner class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageSlider: SliderView
        var tvTiltle: TextView
        var tvDate: TextView
        var tvDescription: TextView
        var ivDelete: ImageView
        var ivEdit: ImageView
        var playerView: PlayerView
        var flVideo: FrameLayout



        init {
            imageSlider=itemView.findViewById(R.id.imageSlider)
            tvDescription=itemView.findViewById(R.id.tvDescription)
            tvDate=itemView.findViewById(R.id.tvDate)
            tvTiltle=itemView.findViewById(R.id.tvTiltle)
            playerView=itemView.findViewById(R.id.exoPlayerView)
            flVideo=itemView.findViewById(R.id.flVideo)
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