package com.tvshow.series.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvshow.series.R

class ImageSliderAdapter(private val imageList:List<String>) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderHolder>() {

    inner class ImageSliderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView:ImageView = itemView.findViewById(R.id.tvShowPictures)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_slider_list,parent,false)
        return ImageSliderHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderHolder, position: Int) {
        val images = imageList[position]
        Picasso.get().load(images).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}