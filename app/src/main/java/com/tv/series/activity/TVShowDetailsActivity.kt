package com.tv.series.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tv.series.R

class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_details)

        supportActionBar!!.title = "Show Details"

        val intent = intent
        val extras = intent.extras

        if (extras != null){
            id = extras.getInt("id").toString()
        }else{
            Log.i("Error Message","Value is empty")
        }

        Toast.makeText(this,id,Toast.LENGTH_SHORT).show()
    }
}