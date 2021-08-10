package com.tv.series.adapter

import android.annotation.SuppressLint
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tv.series.R
import com.tv.series.model.Episode

class EpisodeAdapter(private val image: String, private var episodeList:List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder>() {

    inner class EpisodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageTvShowTwo = itemView.findViewById<ImageView>(R.id.tvShowImageTwo)
        val episodeNumber = itemView.findViewById<TextView>(R.id.episode_number)
        val episodeName = itemView.findViewById<TextView>(R.id.episode_name)
        val episodeAirDate = itemView.findViewById<TextView>(R.id.episode_air_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.episode_list,parent,false)
        return EpisodeHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val episode = episodeList[position]

        holder.episodeNumber.text = "SE${episode.season} EP${episode.episode}"
        holder.episodeName.text = episode.name
        holder.episodeAirDate.text = "Air Date : ${episode.air_date}"

        Picasso.get().load(image).into(holder.imageTvShowTwo)
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}