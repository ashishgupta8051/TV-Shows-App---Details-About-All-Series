package com.tvshow.series.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tvshow.series.R
import com.tvshow.series.model.TVShow
import com.tvshow.series.utils.ClickListener

class TVShowAdapter(private val listener:ClickListener) :
    RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {
    private val tvShowList : ArrayList<TVShow> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val viewHolder = TVShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tv_show_list,parent,false))
        viewHolder.itemView.setOnClickListener {
            listener.onClickedTvShow(tvShowList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = tvShowList[position]
        Picasso.get().load(tvShow.image_thumbnail_path).into(holder.imageView)

        holder.textName.text = tvShow.name
        holder.textNetwork.text = tvShow.network
        holder.textStarted.text = "Started on : "+tvShow.start_date
        holder.textStatus.text = "Status : "+tvShow.status
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getTvShowDetails(list:List<TVShow>){
        tvShowList.clear()
        tvShowList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView:ImageView = itemView.findViewById(R.id.showImage)
        var textName:TextView = itemView.findViewById(R.id.textName)
        var textNetwork:TextView = itemView.findViewById(R.id.textNetwork)
        var textStarted:TextView = itemView.findViewById(R.id.textStarted)
        var textStatus:TextView = itemView.findViewById(R.id.textStatus)
    }

}