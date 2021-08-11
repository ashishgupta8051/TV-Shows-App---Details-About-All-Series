package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tv.series.R
import com.tv.series.adapter.EpisodeAdapter
import com.tv.series.model.Episode
import com.tv.series.viewmodel.TVShowDetailsViewModel

class EpisodeDetails : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel
    private lateinit var id:String
    private lateinit var image:String
    private lateinit var progressBar: ProgressBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edisode_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        recyclerView = findViewById(R.id.episodeRecyclerView)
        progressBar = findViewById(R.id.episodeProgressBar)

        val intent = intent
        val extras = intent.extras

        if (extras != null){
            id = extras.getString("id").toString()
            image = extras.getString("image").toString()
        }else{
            Log.e(TAG,"Data is null")
        }

        tvShowDetailsViewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)

        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)

        loadEpisodeDetails()
    }

    override fun onResume() {
        super.onResume()
        loadEpisodeDetails()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadEpisodeDetails() {
        tvShowDetailsViewModel.getTvShowDetails(id).observe(this){
                response ->
            progressBar.visibility = View.GONE
            supportActionBar!!.title = "Episodes || ${response.tvShow.name}"
            episodeAdapter = EpisodeAdapter(image,response.tvShow.episodes)
            recyclerView.adapter = episodeAdapter
            episodeAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}