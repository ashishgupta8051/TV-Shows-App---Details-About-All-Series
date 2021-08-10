package com.tv.series.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import com.tv.series.R
import com.tv.series.adapter.ImageSliderAdapter
import com.tv.series.response.TVShowDetailsResponse
import com.tv.series.viewmodel.TVShowDetailsViewModel
import java.util.*

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
    private lateinit var tvShowDescription: TextView
    private lateinit var tvShowDescriptionMore: TextView
    private lateinit var tvShowGenres: TextView
    private lateinit var viewDivider1: View
    private lateinit var viewDivider2: View
    private lateinit var linearLayout2: LinearLayout
    private lateinit var tvShowRating: TextView
    private lateinit var tvShowRuntime: TextView
    private lateinit var downloadBtn: Button
    private lateinit var episodeBtn: Button


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
        tvShowDescription = findViewById(R.id.tvShowDescription)
        tvShowDescriptionMore = findViewById(R.id.tvShowDescriptionMore)
        tvShowGenres = findViewById(R.id.tvShowGenres)
        viewDivider1 = findViewById(R.id.viewDivider1)
        viewDivider2 = findViewById(R.id.viewDivider2)
        linearLayout2 = findViewById(R.id.linearLayout)
        tvShowRating = findViewById(R.id.tvShowRating)
        tvShowRuntime = findViewById(R.id.tvShowRuntime)
        downloadBtn = findViewById(R.id.button1)
        episodeBtn = findViewById(R.id.button2)

        //Initialize TvShowDetailsViewModel
        tvShowDetailsViewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)

        //Get details about tv series
        getDetailsOfMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDetailsOfMovie() {
        tvShowDetailsViewModel.getTvShowDetails(id).observe(this,{
            response ->
            tvShowDetailsProgressBar.visibility = View.GONE
            loadViewPagerImages(response.tvShow.pictures)
            loadBasicDetails(response)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun loadBasicDetails(response: TVShowDetailsResponse?) {
        tvShowDescriptionMore.visibility = View.VISIBLE
        viewDivider1.visibility = View.VISIBLE
        viewDivider2.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        downloadBtn.visibility = View.VISIBLE
        episodeBtn.visibility = View.VISIBLE

        Picasso.get().load(response!!.tvShow.image_thumbnail_path).into(imageTvShow)
        tvShowName.text = response.tvShow.name
        tvShowNetwork.text = response.tvShow.network + " ("+response.tvShow.country+")"
        tvShowStarted.text = "Started on : "+response.tvShow.start_date
        tvShowStatus.text = "Status : "+response.tvShow.status
        tvShowDescription.text = HtmlCompat.fromHtml(response.tvShow.description,HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        tvShowDescriptionMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(response.tvShow.url)
            startActivity(intent)
        }
        var listString = ""
        for (item in response.tvShow.genres) listString += "$item , "
        tvShowGenres.text = listString

        tvShowRuntime.text = response.tvShow.runtime.toString()+" Min"
        tvShowRating.text = String.format(Locale.getDefault(),"%.2f",response.tvShow.rating.toDouble())

        episodeBtn.setOnClickListener {
            val intent = Intent(this,EpisodeDetails::class.java)
            intent.putExtra("id",id)
            intent.putExtra("image",response.tvShow.image_thumbnail_path)
            startActivity(intent)
        }
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