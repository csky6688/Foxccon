package com.drkj.foxconn.util

/**
 * 时间日期工具
 * Created by VeronicaRen on 2018/2/28.
 */

object DateUtil {
    fun getTime(): String {
        val currentTime = System.currentTimeMillis()
        return currentTime.toString()
    }
}
