package com.drkj.foxconn.activties

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.drkj.foxconn.util.SpUtil

/**
 * 基础Activity
 * Created by VeronicaRen on 2018/2/23 in Kotlin
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initView()
    }

    abstract fun setLayout(): Int

    abstract fun initView()

    fun getToken(): String = SpUtil.getString(this, "token")
}