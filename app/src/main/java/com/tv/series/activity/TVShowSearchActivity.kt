package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tv.series.R
import com.tv.series.adapter.TVShowAdapter
import com.tv.series.model.TVShow
import com.tv.series.utils.ClickListener
import com.tv.series.viewmodel.TVShowSearchViewModel
import com.tv.series.viewmodel.TVShowViewModel
import kotlin.collections.ArrayList

class TVShowSearchActivity : AppCompatActivity(),ClickListener {
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar: ProgressBar
    private var searchList:ArrayList<TVShow> = arrayListOf()
    private lateinit var allTVShowAdapter:TVShowAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvShowSearchViewModel: TVShowSearchViewModel
    private var currentPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_search)

        supportActionBar!!.title = "Search"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setUpUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.searchmenu,menu)
        val item = menu!!.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Search Here ...."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                getTvShowSearchResult(query)

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1)){
                            if (currentPage <= totalPages){
                                currentPage += 1
                                getTvShowSearchResult(query)
                                Toast.makeText(this@TVShowSearchActivity,"Hi2",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                getTvShowSearchResult(query)

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1)){
                            if (currentPage <= totalPages){
                                currentPage += 1
                                getTvShowSearchResult(query)
                                Toast.makeText(this@TVShowSearchActivity,"Hi",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpUI() {
        tvShowSearchViewModel = ViewModelProvider(this).get(TVShowSearchViewModel::class.java)
        progressBar = findViewById(R.id.searchProgressBar)
        recyclerView = findViewById(R.id.tvShowSearchRecycler)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        allTVShowAdapter = TVShowAdapter(searchList,this)
        recyclerView.adapter = allTVShowAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getTvShowSearchResult(searchQuery:String) {
        tvShowSearchViewModel.getSearchResult(searchQuery,currentPage).observe(this){
            response ->
            if (response != null){
                progressBar.visibility = View.GONE
                totalPages = response.pages
                val count = searchList.size
                searchList.addAll(response.tv_shows)
                allTVShowAdapter.notifyItemRangeInserted(count,searchList.size)
            }else{
                progressBar.visibility = View.GONE
                Log.e(TAG,"response is null")
            }
        }
    }

}