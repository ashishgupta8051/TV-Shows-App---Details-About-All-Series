package com.tvshow.series.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.tvshow.series.R
import com.tvshow.series.connection.CheckInternetConnection
import java.lang.Exception

class TVShowDownloadActivity : AppCompatActivity() {
    private lateinit var name:String
    private lateinit var btn1:Button
    private lateinit var btn2:Button
    private lateinit var btn3:Button
    private lateinit var btn4:Button
    private lateinit var btn5:Button
    private lateinit var btn6:Button
    private lateinit var btn7:Button
    private lateinit var btn8:Button
    private lateinit var btn9:Button
    private lateinit var btn10:Button
    private lateinit var btn11:Button
    private lateinit var btnVpn1:Button
    private lateinit var broadcastReceiver: BroadcastReceiver

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_download)

        supportActionBar!!.title = "Download Link"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val intent = intent
        val extras = intent.extras

        if (extras != null){
            name = extras.getString("name").toString()
        }else{
            Log.e(TAG,"Value is null")
        }

        setUpUI()

        //https://themovieverse.com/
        btn1.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://themovieverse.com/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://moviesverse.org.in/
        btn2.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://moviesverse.org.in/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://katmoviehd.sx/
        btn3.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://katmoviehd.sx/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://themoviesflix.in.net/
        btn4.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://themoviesflix.in.net/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://todaytvseries.one/
        btn5.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://todaytvseries.one/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://ev01.to/search/
        btn6.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://ev01.to/search/$name");
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://moviesrush.one/
        btn7.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://moviesrush.one/${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://moviezaddiction.co/
        btn8.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://moviezaddiction.co/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://1kmovies.me/
        btn9.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://1kmovies.me/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://www.mkvcinemas.in/
        btn10.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.mkvcinemas.in/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //https://9xmovies.quest/
        btn11.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://9xmovies.quest/?s=${name}")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }

        //http://www.todaytvseries1.com/
        btnVpn1.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://www.todaytvseries1.com")
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,"Some thing is wrong.", Toast.LENGTH_SHORT).show()
            }
        }


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
        btn1 = findViewById(R.id.downloadLink1)
        btn2 = findViewById(R.id.downloadLink2)
        btn3 = findViewById(R.id.downloadLink3)
        btn4 = findViewById(R.id.downloadLink4)
        btn5 = findViewById(R.id.downloadLink5)
        btn6 = findViewById(R.id.downloadLink6)
        btn7 = findViewById(R.id.downloadLink7)
        btn8 = findViewById(R.id.downloadLink8)
        btn9 = findViewById(R.id.downloadLink9)
        btn10 = findViewById(R.id.downloadLink10)
        btn11 = findViewById(R.id.downloadLink11)
        btnVpn1 = findViewById(R.id.downloadVpnLink1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}