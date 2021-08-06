package com.tv.series.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tv.series.R
import com.tv.series.viewmodel.TVShowViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var tvShowViewModel: TVShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvShowViewModel = ViewModelProvider(this).get(TVShowViewModel::class.java)

        tvShowViewModel.getTvShow(0,this).observe(this, Observer {
            response ->
            Log.e(TAG,response.toString())
        })
    }
}