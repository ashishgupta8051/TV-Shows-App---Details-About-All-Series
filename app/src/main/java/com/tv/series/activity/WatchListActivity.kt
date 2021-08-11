package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tv.series.R
import com.tv.series.adapter.WatchListAdapter
import com.tv.series.model.TVShow
import com.tv.series.utils.WatchListListener
import com.tv.series.viewmodel.WatchListViewModel

class WatchListActivity : AppCompatActivity(),WatchListListener {
    private lateinit var watchListViewModel: WatchListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var watchList:ArrayList<TVShow> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)

        supportActionBar!!.title = "Your WatchList"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        //Initialize watchlist view model
        watchListViewModel = ViewModelProvider(this).get(WatchListViewModel::class.java)

        recyclerView = findViewById(R.id.watchListRecyclerView)
        progressBar = findViewById(R.id.watchListProgressBar)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager

        getWatchList()
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
            watchList.clear()
            watchList.addAll(it)
            watchListAdapter = WatchListAdapter(it,this)
            recyclerView.adapter = watchListAdapter
            watchListAdapter.notifyDataSetChanged()
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