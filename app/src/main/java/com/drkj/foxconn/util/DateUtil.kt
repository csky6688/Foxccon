package com.drkj.foxconn.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间日期工具
 * Created by VeronicaRen on 2018/2/28.
 */

object DateUtil {
    fun getTime(): String {
        val currentTime = System.currentTimeMillis()
        return currentTime.toString()
    }

    fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        return formatter.format(calendar.time)
    }
}
