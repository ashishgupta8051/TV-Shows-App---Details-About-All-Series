package com.tvshow.series.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvshow.series.R
import com.tvshow.series.adapter.EpisodeAdapter
import com.tvshow.series.connection.CheckInternetConnection
import com.tvshow.series.viewmodel.TVShowDetailsViewModel

class EpisodeDetails : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel
    private lateinit var id:String
    private lateinit var image:String
    private lateinit var progressBar: ProgressBar
    private lateinit var broadcastReceiver: BroadcastReceiver

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edisode_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        broadcastReceiver = CheckInternetConnection()
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
        episodeAdapter = EpisodeAdapter(image)
        recyclerView.adapter = episodeAdapter
    }

    override fun onStart() {
        super.onStart()
        loadEpisodeDetails()
        var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadEpisodeDetails() {
        tvShowDetailsViewModel.getTvShowDetails(id).observe(this){
                response ->
            progressBar.visibility = View.GONE
            supportActionBar!!.title = "Episodes || ${response.tvShow.name}"
            episodeAdapter.getEpisode(response.tvShow.episodes)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}