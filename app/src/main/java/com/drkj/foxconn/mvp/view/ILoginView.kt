package com.drkj.foxconn.mvp.view

import com.drkj.foxconn.mvp.IBaseView

/**
 * Created by VeronicaRen on 2018/2/23.
 */
interface ILoginView : IBaseView {

    fun onLoginStart()

    fun onLoginFinish(token: String)

    fun onLoginFailed()

    fun onReceiveMsg(msg: String)
}