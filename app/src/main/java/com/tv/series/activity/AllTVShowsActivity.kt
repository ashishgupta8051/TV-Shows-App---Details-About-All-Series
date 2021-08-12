package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tv.series.R
import com.tv.series.adapter.TVShowAdapter
import com.tv.series.model.TVShow
import com.tv.series.utils.ClickListener
import com.tv.series.viewmodel.TVShowViewModel

class AllTVShowsActivity : AppCompatActivity(),ClickListener {
    private lateinit var tvShowViewModel: TVShowViewModel
    private lateinit var tvShowAdapter: TVShowAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollProgress: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentPage = 1
    private var totalPages = 1
    private var tvShowList:ArrayList<TVShow> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tv_shows)

        //action bar title name
        supportActionBar!!.title = "TV Shows | Most Popular"

        //setUpUI
        setUpUI()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuitem,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search){
            startActivity(Intent(this,TVShowSearchActivity::class.java))
        }else if (item.itemId == R.id.action_eye){
            startActivity(Intent(this,WatchListActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickedTvShow(tvShow: TVShow) {
        val intent = Intent(this,TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow",tvShow)
        startActivity(intent)
    }

    private fun setUpUI() {
        progressBar = findViewById(R.id.allTvShowLoader)
        scrollProgress = findViewById(R.id.scrollProgressBar)
        recyclerView = findViewById(R.id.tvShowRecyclerView)
        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        tvShowAdapter = TVShowAdapter(tvShowList,this)
        recyclerView.adapter = tvShowAdapter

        //initialize TVShowViewModel
        tvShowViewModel = ViewModelProvider(this).get(TVShowViewModel::class.java)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    if (currentPage <= totalPages){
                        currentPage += 1
                        scrollProgress.visibility = View.VISIBLE
                        loadPopularTvShow()
                    }
                }
            }
        })

        //get list of all tv series
        loadPopularTvShow()
    }

    private fun loadPopularTvShow() {
        tvShowViewModel.getTvShow(currentPage).observe(this,{
                response ->
            if (response != null){
                progressBar.visibility = View.GONE
                scrollProgress.visibility = View.GONE
                totalPages = response.pages
                val count = tvShowList.size
                tvShowList.addAll(response.tv_shows)
                tvShowAdapter.notifyItemRangeInserted(count,tvShowList.size)
            }else{
                progressBar.visibility = View.GONE
            }
        })
    }



}