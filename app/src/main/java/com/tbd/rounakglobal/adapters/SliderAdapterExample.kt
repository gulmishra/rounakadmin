package com.tbd.rounakglobal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.tbd.rounakglobal.R


class SliderAdapterExample(private val context: Context) :
    SliderViewAdapter<SliderAdapterExample.SliderAdapterVH>() {
    private var mSliderItems: MutableList<String> = ArrayList<String>()

    fun renewItems(sliderItems: MutableList<String>) {
        this.mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: String) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: String = mSliderItems[position]


        Glide.with(viewHolder.itemView)
            .load(sliderItem)
            .fitCenter()
            .into(viewHolder.imageViewBackground)


    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
//        var itemView: View
//        var imageViewBackground: ImageView =
//            itemView.findViewById<ImageView>(R.id.iv_auto_image_slider)
//        var imageGifContainer: ImageView = itemView.findViewById<ImageView>(R.id.iv_gif_container)
//        var textViewDescription: TextView =
//            itemView.findViewById<TextView>(R.id.tv_auto_image_slider)
//
//        init {
//            this.itemView = itemView
//        }
        var imageViewBackground: ImageView


        init {
            imageViewBackground=itemView.findViewById(R.id.iv_auto_image_slider)

        }
    }
}