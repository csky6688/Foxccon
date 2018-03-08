package com.drkj.foxconn.activties

import android.app.Dialog
import android.content.Intent
import android.widget.*
import com.drkj.foxconn.R
import com.drkj.foxconn.mvp.presenter.LoginPresenter
import com.drkj.foxconn.mvp.view.ILoginView
import com.drkj.foxconn.util.SpUtil
import kotterknife.bindView

/**
 * 新登录界面
 * Created by VeronicaRen on 2018/2/23.
 */
class NewLoginActivity : BaseActivity(), ILoginView {

    private val presenter = LoginPresenter()

    private val userName: EditText by bindView(R.id.et_username)

    private val password: EditText by bindView(R.id.et_password)

    private val remember: CheckBox by bindView(R.id.checkbox_remember)

    private val loginButton: ImageButton by bindView(R.id.button_login)

    private val tvVersion: TextView by bindView(R.id.login_tv_version)

    private var dialog: Dialog? = null

    override fun setLayout(): Int = R.layout.activity_login

    override fun initView() {
        presenter.bindView(this)
        dialog = Dialog(this)
        userName.setText(SpUtil.getString(this, "username"))
        password.setText(SpUtil.getString(this, "password"))
        remember.isChecked = SpUtil.getBlooean(this, "remember")

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
            SpUtil.putString(this, "username", userName.text.toString())
            SpUtil.putString(this, "password", password.text.toString())
        } else {
            SpUtil.putString(this, "username", "")
            SpUtil.putString(this, "password", "")
        }
        SpUtil.putString(this, "token", token)
        SpUtil.putBlooean(this, "remember", remember.isChecked)
        dialog?.dismiss()
//        startActivity(Intent(this, MainActivity::class.java))
        startActivity(Intent(this, NewMainActivity::class.java))
//        finish()
    }

    override fun onLoginFailed() {
        loginButton.isEnabled = true
        if (remember.isChecked) {
            SpUtil.putString(this, "username", userName.text.toString())
            SpUtil.putString(this, "password", password.text.toString())
        } else {
            SpUtil.putString(this, "username", "")
            SpUtil.putString(this, "password", "")
        }
        SpUtil.putBlooean(this, "remember", remember.isChecked)
        dialog?.dismiss()
        Toast.makeText(this, "服务器连接失败，请重试或检查网络连接", Toast.LENGTH_SHORT).show()
    }

    override fun onReceiveMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destory()
    }
}