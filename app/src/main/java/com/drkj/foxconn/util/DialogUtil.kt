package com.drkj.foxconn.util

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.drkj.foxconn.R

/**
 * Created by VeronicaRen on 2018/3/22 in Kotlin
 */
object DialogUtil {

    fun showLoadingDialog(context: Context) {
        val dialog = AlertDialog.Builder(context)
                .setView(R.layout.dialog_data_synchronization_hint)
                .create()


//        dialog.findViewById<TextView>(R.id.dialog_message)!!.text
    }
}