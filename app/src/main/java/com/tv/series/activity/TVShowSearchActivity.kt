package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tv.series.R
import com.tv.series.adapter.TVShowAdapter
import com.tv.series.model.TVShow
import com.tv.series.utils.ClickListener
import com.tv.series.viewmodel.TVShowSearchViewModel
import com.tv.series.viewmodel.TVShowViewModel
import java.util.*
import kotlin.collections.ArrayList

class TVShowSearchActivity : AppCompatActivity(),ClickListener {
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchBackImage: ImageView
    private lateinit var searchIcon: ImageView
    private lateinit var searchEditBox: EditText
    private var searchList:ArrayList<TVShow> = arrayListOf()
    private lateinit var allTVShowAdapter:TVShowAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvShowSearchViewModel: TVShowSearchViewModel
    private var currentPage = 1
    private var totalPages = 1
    private val timer:Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_search)

        supportActionBar!!.hide()

        setUpUI()
    }


    override fun onClickedTvShow(tvShow: TVShow) {
        val intent = Intent(this,TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow",tvShow)
        startActivity(intent)
    }

    private fun setUpUI() {
        tvShowSearchViewModel = ViewModelProvider(this).get(TVShowSearchViewModel::class.java)
        searchBackImage = findViewById(R.id.searchBackButton)
        searchIcon = findViewById(R.id.searchIcon)
        searchEditBox = findViewById(R.id.searchQueryEditBox)
        progressBar = findViewById(R.id.searchProgressBar)
        recyclerView = findViewById(R.id.tvShowSearchRecycler)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        allTVShowAdapter = TVShowAdapter(searchList,this)
        recyclerView.adapter = allTVShowAdapter

        searchEditBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {
                if (searchEditBox.text.toString().isNotEmpty()){
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalPages = 1
                                getTvShowSearchResult(searchEditBox.text.toString())
                            }
                        }
                    },800)
                }else{
                    searchList.clear()
                    allTVShowAdapter.notifyDataSetChanged()
                }
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)){
                    if (currentPage <= totalPages){
                        currentPage += 1
                        getTvShowSearchResult(searchEditBox.text.toString())
                    }
                }
            }
        })

        searchEditBox.requestFocus()

        searchBackImage.setOnClickListener {
            onBackPressed()
        }
    }

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