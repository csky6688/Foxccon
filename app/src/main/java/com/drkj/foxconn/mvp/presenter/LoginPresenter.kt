package com.drkj.foxconn.mvp.presenter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.drkj.foxconn.db.DbController
import com.drkj.foxconn.mvp.BasePresenter
import com.drkj.foxconn.mvp.view.ILoginView
import com.drkj.foxconn.net.NetClient
import com.drkj.foxconn.util.SpUtil
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * NewLoginActivity推荐者
 * Created by VeronicaRen on 2018/2/23.
 */
class LoginPresenter : BasePresenter<ILoginView>() {

    fun login(userName: EditText, password: EditText) {
        rootView!!.onLoginStart()
        if (TextUtils.isEmpty(userName.text.toString()) || TextUtils.isEmpty(password.text.toString())) {
            rootView!!.onReceiveMsg("帐号和密码不能为空")
        }

        NetClient.getInstance().api.getToken(userName.text.toString(), password.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.code() == 200 -> {
                            val token = it.body()!!.string()
                            rootView!!.onLoginFinish(token)
                        }
                        it.code() == 404 -> {
                            rootView!!.onReceiveMsg("帐号或者密码错误")
                            rootView!!.onLoginFailed()
                        }
                        else -> {
                            rootView!!.onReceiveMsg("服务器异常")
                            rootView!!.onLoginFailed()
                        }
                    }
                }, {
                    rootView!!.onLoginFailed()
                })
    }

    fun getUserInfo(context: Context) {
        NetClient.getInstance().api.getTypeUser(SpUtil.getString(context, SpUtil.TOKEN))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(Gson().toJson(it))
                    when (it.respCode) {
                        "0" -> {
                            it.data.filter { it.userAccount == SpUtil.getString(context, SpUtil.USERNAME) }
                                    .forEach {
                                        SpUtil.putString(context, SpUtil.REAL_NAME, it.userName)
                                        SpUtil.putString(context, SpUtil.TASK_TYPE, it.equipmentType)
                                        SpUtil.putString(context, SpUtil.USER_ID, it.userId)
                                        SpUtil.putString(context, SpUtil.TASK_ID, "")//清除taskId
                                    }
                            if (!TextUtils.isEmpty(SpUtil.getString(context, SpUtil.REAL_NAME)) && !TextUtils.isEmpty(SpUtil.getString(context, SpUtil.TASK_TYPE))) {
                                rootView!!.onUserInfoFinish()
                            } else {
                                rootView!!.onUserInfoFailed()
                            }
                        }
                        else -> rootView!!.onUserInfoFailed()
                    }
                }, {
                    rootView!!.onLoginFailed()
                })
    }
}