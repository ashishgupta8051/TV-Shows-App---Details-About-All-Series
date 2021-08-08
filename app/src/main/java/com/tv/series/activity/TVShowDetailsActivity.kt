package com.tv.series.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import com.tv.series.R
import com.tv.series.adapter.ImageSliderAdapter
import com.tv.series.response.TVShowDetailsResponse
import com.tv.series.viewmodel.TVShowDetailsViewModel




class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var id:String
    private lateinit var tvShowDetailsViewModel:TVShowDetailsViewModel
    private lateinit var tvShowDetailsProgressBar: ProgressBar
    private lateinit var viewPager: ViewPager2
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var linearLayout: LinearLayout
    private lateinit var imageTvShow:ImageView
    private lateinit var tvShowName: TextView
    private lateinit var tvShowNetwork: TextView
    private lateinit var tvShowStarted: TextView
    private lateinit var tvShowStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_details)

        supportActionBar!!.title = "Show Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        //Get data from intent
        val intent = intent
        val extras = intent.extras

        if (extras != null){
            id = extras.getInt("id").toString()
        }else{
            Log.i("Error Message","Value is empty")
        }

        //Binding data
        tvShowDetailsProgressBar = findViewById(R.id.tvShowDetailsLoader)
        viewPager = findViewById(R.id.viewPager)
        linearLayout = findViewById(R.id.indicatorLayoutManager)
        imageTvShow = findViewById(R.id.tvShowImage)
        tvShowName = findViewById(R.id.tvShowName)
        tvShowNetwork = findViewById(R.id.tvShowNetwork)
        tvShowStarted = findViewById(R.id.tvShowStarted)
        tvShowStatus = findViewById(R.id.tvShowStatus)

        //Initialize TvShowDetailsViewModel
        tvShowDetailsViewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)

        //Get details about tv series
        getDetailsOfMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getDetailsOfMovie() {
        tvShowDetailsViewModel.getTvShowDetails(id).observe(this,{
            response ->
            tvShowDetailsProgressBar.visibility = View.GONE
            loadViewPagerImages(response.tvShowDetails.pictures)
            loadBasicDetails(response)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadBasicDetails(response: TVShowDetailsResponse?) {
        Picasso.get().load(response!!.tvShowDetails.image_thumbnail_path).into(imageTvShow)
        tvShowName.text = response!!.tvShowDetails.name
        tvShowNetwork.text = response.tvShowDetails.network + " ("+response.tvShowDetails.country+")"
        tvShowStarted.text = "Started on : "+response.tvShowDetails.start_date
        tvShowStatus.text = "Status : "+response.tvShowDetails.status
    }

    private fun loadViewPagerImages(pictures: List<String>) {
        viewPager.offscreenPageLimit = 1
        imageSliderAdapter = ImageSliderAdapter(pictures)
        viewPager.adapter = imageSliderAdapter
        setupSliderIndicator(pictures.size)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicator(size: Int) {
        val indicator = arrayOfNulls<ImageView>(size)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(applicationContext)
            indicator[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,R.drawable.image_selector_inactive
                )
            )
            indicator[i]!!.layoutParams = params
            linearLayout.addView(indicator[i])
        }
        setCurrentSliderIndicator(0)
    }

    fun setCurrentSliderIndicator(position :Int){
        val count = linearLayout.childCount
        for (item in 0 until count){
            val imageView:ImageView = linearLayout.getChildAt(item) as ImageView
            if (item == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.image_selector_active))
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.image_selector_inactive))
            }
        }
    }
}