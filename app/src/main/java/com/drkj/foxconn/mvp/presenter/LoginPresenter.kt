package com.drkj.foxconn.mvp.presenter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.drkj.foxconn.db.DbController
import com.drkj.foxconn.mvp.BasePresenter
import com.drkj.foxconn.mvp.view.ILoginView
import com.drkj.foxconn.net.NetClient
import com.drkj.foxconn.util.FileUtil
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
                            rootView!!.onReceiveMsg("帐号密码错误或无法访问服务器:404")
                        }
                        else -> {
                            rootView!!.onReceiveMsg("服务器异常")
                        }
                    }
                }, {
                    rootView!!.onLoginFailed(it)
                })
    }

    fun getUserInfo(context: Context, userName: EditText, password: EditText) {
        val token = SpUtil.getString(context, SpUtil.TOKEN)

        NetClient.getInstance().api.getTypeUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    var realName: String? = null

                    var taskType: String? = null

                    var userId: String? = null

                    for (item in it.data) {
                        if (item.userAccount == userName.text.toString()) {
                            realName = item.userName
                            taskType = item.equipmentType
                            userId = item.userId

                            SpUtil.putString(context, SpUtil.REAL_NAME, item.userName)
                            SpUtil.putString(context, SpUtil.TASK_TYPE, item.equipmentType)
                            SpUtil.putString(context, SpUtil.USER_ID, item.userId)
                            SpUtil.putString(context, SpUtil.TASK_ID, "")
                            break
                        }
                    }

                    if (!TextUtils.isEmpty(realName) && !TextUtils.isEmpty(taskType)) {
                        rootView!!.onUserInfoFinish()
                    } else {
                        rootView!!.onUserInfoFailed("realName:$realName\ntaskType:$taskType")
                    }
                }, {
                    rootView!!.onLoginFailed(it)
                })
    }
}