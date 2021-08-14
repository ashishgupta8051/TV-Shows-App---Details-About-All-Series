package com.tvshow.series.connection

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.tvshow.series.R
import com.tvshow.series.utils.NetworkUtil

class CheckInternetConnection : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var result = context?.let { NetworkUtil().getNetworkStatus(it) }

        //Create Alert Dialog
        var builder: AlertDialog.Builder = context?.let { AlertDialog.Builder(it) }!!
        var view: View = LayoutInflater.from(context).inflate(R.layout.internetdialogbox,null)
        builder.setView(view)

        var button: Button = view.findViewById(R.id.retry_btn)

        var alertDialog = builder.setCancelable(false).create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.attributes.windowAnimations = android.R.style.Animation_Toast

        button.setOnClickListener {
            alertDialog.dismiss()
            onReceive(context,intent)
        }

        if (result == 0){
            alertDialog.show()
        }else if(result == 1 || result == 2){
            alertDialog.dismiss()
        }
    }
}