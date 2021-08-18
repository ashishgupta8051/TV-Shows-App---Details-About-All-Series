package com.tvshow.series.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvshow.series.R
import com.tvshow.series.adapter.WatchListAdapter
import com.tvshow.series.connection.CheckInternetConnection
import com.tvshow.series.model.TVShow
import com.tvshow.series.utils.WatchListListener
import com.tvshow.series.viewmodel.WatchListViewModel

class WatchListActivity : AppCompatActivity(),WatchListListener {
    private lateinit var watchListViewModel: WatchListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)

        supportActionBar!!.title = "Your WatchList"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        //Initialize watchlist view model
        watchListViewModel = ViewModelProvider(this).get(WatchListViewModel::class.java)

        broadcastReceiver = CheckInternetConnection()
        recyclerView = findViewById(R.id.watchListRecyclerView)
        progressBar = findViewById(R.id.watchListProgressBar)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        watchListAdapter = WatchListAdapter(this)
        recyclerView.adapter = watchListAdapter
    }

    override fun onStart() {
        super.onStart()
        //get Watch List
        getWatchList()
        var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        getWatchList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getWatchList() {
        progressBar.visibility = View.GONE
        watchListViewModel.getWatchList().observe(this){
            watchListAdapter.getWatchList(it)
        }
    }

    override fun onTvShowClicked(tvShow: TVShow) {
        val intent = Intent(this,TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow",tvShow)
        startActivity(intent)
    }

    override fun removeTvShow(tvShow: TVShow) {
        watchListViewModel.removeFromWatchList(tvShow)
    }
}