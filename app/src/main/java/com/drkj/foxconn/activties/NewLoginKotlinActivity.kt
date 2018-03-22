package com.drkj.foxconn.activties

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.KeyEvent
import android.widget.*
import com.drkj.foxconn.R
import com.drkj.foxconn.mvp.presenter.LoginPresenter
import com.drkj.foxconn.mvp.view.ILoginView
import com.drkj.foxconn.util.SpUtil
import kotterknife.bindView

/**
 * 新登录界面
 * Created by VeronicaRen on 2018/2/23. in Kotlin
 */
class NewLoginKotlinActivity : BaseKotlinActivity(), ILoginView {

    private val presenter = LoginPresenter()

    private val userName: EditText by bindView(R.id.et_username)

    private val password: EditText by bindView(R.id.et_password)

    private val remember: CheckBox by bindView(R.id.checkbox_remember)

    private val loginButton: ImageButton by bindView(R.id.button_login)

    private val tvVersion: TextView by bindView(R.id.login_tv_version)

    private var dialog: Dialog? = null

    private var exitDialog: AlertDialog? = null

    override fun setLayout(): Int = R.layout.activity_login

    override fun initView() {
        presenter.bindView(this)
        dialog = Dialog(this)
        userName.setText(SpUtil.getString(this, SpUtil.USERNAME))
        password.setText(SpUtil.getString(this, SpUtil.PASSWORD))
        remember.isChecked = SpUtil.getBoolean(this, SpUtil.REMEMBER)

        loginButton.setOnClickListener {
            it.isEnabled = false
            presenter.login(userName, password)
        }

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        tvVersion.text = packageInfo.versionName
    }

    override fun onLoginStart() {
        loginButton.isEnabled = false
        dialog?.setContentView(R.layout.dialog_data_synchronization_hint)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun onLoginFinish(token: String) {
        loginButton.isEnabled = true
        if (remember.isChecked) {
            SpUtil.putString(this, SpUtil.USERNAME, userName.text.toString())
            SpUtil.putString(this, SpUtil.PASSWORD, password.text.toString())
        } else {
            SpUtil.putString(this, SpUtil.USERNAME, "")
            SpUtil.putString(this, SpUtil.PASSWORD, "")
        }
        SpUtil.putString(this, SpUtil.TOKEN, token)
        SpUtil.putBoolean(this, SpUtil.REMEMBER, remember.isChecked)
        dialog?.dismiss()

        presenter.getUserInfo(this)
    }

    override fun onLoginFailed() {
        loginButton.isEnabled = true
        if (remember.isChecked) {
            SpUtil.putString(this, SpUtil.USERNAME, userName.text.toString())
            SpUtil.putString(this, SpUtil.PASSWORD, password.text.toString())
        } else {
            SpUtil.putString(this, SpUtil.USERNAME, "")
            SpUtil.putString(this, SpUtil.PASSWORD, "")
        }
        SpUtil.putBoolean(this, SpUtil.REMEMBER, remember.isChecked)
        dialog?.dismiss()
        Toast.makeText(this, "登录失败，请重试或检查网络连接", Toast.LENGTH_SHORT).show()
    }

    override fun onUserInfoFinish() {
        startActivity(Intent(this, NewMainKotlinActivity::class.java))
//        finish()
    }

    override fun onUserInfoFailed() {
        Toast.makeText(this, "获取用户信息失败，请检查用户数据是否录入正确", Toast.LENGTH_SHORT).show()
    }

    override fun onReceiveMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                exitDialog = AlertDialog.Builder(this)
                        .setTitle("退出")
                        .setMessage("是否退出？")
                        .setNegativeButton("取消", object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.dismiss()
                            }
                        })
                        .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                finish()
                            }
                        })
                        .create()
                exitDialog!!.show()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destory()
    }
}