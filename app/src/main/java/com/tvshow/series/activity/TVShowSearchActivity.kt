package com.tvshow.series.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvshow.series.R
import com.tvshow.series.adapter.TVShowSearchAdapter
import com.tvshow.series.connection.CheckInternetConnection
import com.tvshow.series.model.TVShow
import com.tvshow.series.utils.ClickListener
import com.tvshow.series.viewmodel.TVShowSearchViewModel
import kotlin.collections.ArrayList

class TVShowSearchActivity : AppCompatActivity(),ClickListener {
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar: ProgressBar
    private var searchList:ArrayList<TVShow> = arrayListOf()
    private lateinit var tvShowSearchAdapter:TVShowSearchAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvShowSearchViewModel: TVShowSearchViewModel
    private var currentPage = 1
    private var totalPages = 1
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_search)

        supportActionBar!!.title = "Search"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        setUpUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickedTvShow(tvShow: TVShow) {
        val intent = Intent(this,TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow",tvShow)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.searchmenu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search here !!"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    currentPage = 1
                    totalPages = 1
                    getTvShowSearchResult(query)
                    scrollRecyclerView(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun scrollRecyclerView(query: String) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    if (currentPage <= totalPages){
                        currentPage += 1
                        getTvShowSearchResult(query)
                    }
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun setUpUI() {
        broadcastReceiver = CheckInternetConnection()
        tvShowSearchViewModel = ViewModelProvider(this).get(TVShowSearchViewModel::class.java)
        progressBar = findViewById(R.id.searchProgressBar)
        recyclerView = findViewById(R.id.tvShowSearchRecycler)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        tvShowSearchAdapter = TVShowSearchAdapter(searchList,this)
        recyclerView.adapter = tvShowSearchAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getTvShowSearchResult(searchQuery:String) {
        progressBar.visibility = View.VISIBLE
        tvShowSearchViewModel.getSearchResult(searchQuery,currentPage).observe(this,{
                response ->
            if (response != null){
                progressBar.visibility = View.GONE
                totalPages = response.pages
                val count = searchList.size
                searchList.addAll(response.tv_shows)
                tvShowSearchAdapter.notifyItemRangeInserted(count,searchList.size)
            }else{
                progressBar.visibility = View.GONE
            }
        })
    }

}