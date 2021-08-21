package com.tvshow.series.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvshow.series.R
import com.tvshow.series.model.TVShow
import com.tvshow.series.utils.ClickListener

class TVShowSearchAdapter(private val listener: ClickListener):
    RecyclerView.Adapter<TVShowSearchAdapter.TVShowSearchViewHolder>() {
    private val tvShowSearchList : ArrayList<TVShow> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowSearchViewHolder {
        val viewHolder = TVShowSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tv_show_list, parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            listener.onClickedTvShow(tvShowSearchList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TVShowSearchViewHolder, position: Int) {
        val tvShow = tvShowSearchList[position]
        Picasso.get().load(tvShow.image_thumbnail_path).into(holder.imageView)

        holder.textName.text = tvShow.name
        holder.textNetwork.text = tvShow.network
        holder.textStarted.text = "Started on : " + tvShow.start_date
        holder.textStatus.text = "Status : " + tvShow.status
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getSearchList(list:List<TVShow>){
        tvShowSearchList.clear();
        tvShowSearchList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tvShowSearchList.size
    }

    inner class TVShowSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.showImage)
        var textName: TextView = itemView.findViewById(R.id.textName)
        var textNetwork: TextView = itemView.findViewById(R.id.textNetwork)
        var textStarted: TextView = itemView.findViewById(R.id.textStarted)
        var textStatus: TextView = itemView.findViewById(R.id.textStatus)
    }

}