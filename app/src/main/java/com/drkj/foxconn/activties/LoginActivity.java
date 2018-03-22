package com.drkj.foxconn.activties;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.net.NetClient;
import com.drkj.foxconn.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText userName;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.checkbox_remember)
    CheckBox remember;
    @BindView(R.id.button_login)
    ImageButton loginButton;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        break;
                }
            }
        };

        userName.setText(SpUtil.getString(this, "username"));
        password.setText(SpUtil.getString(this, "password"));
        remember.setChecked(SpUtil.getBoolean(this, "check"));
    }

    @OnClick(R.id.button_login)
    void login() {
//        loginButton.setEnabled(false);
        if (TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "帐号和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SpUtil.putBoolean(this, "check", remember.isChecked());
        if (remember.isChecked()) {
            SpUtil.putString(this, "username", userName.getText().toString());
            SpUtil.putString(this, "password", password.getText().toString());
        }
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        NetClient.getInstance().getApi().getToken(userName.getText().toString(), password.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> response) throws Exception {
                        loginButton.setEnabled(true);
                        if (response.code() == 200) {
                            String token = response.body().string();
                            SpUtil.putString(LoginActivity.this, "token", token);
//                            mHandler.sendEmptyMessage(1);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "帐号或者密码错误", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginButton.setEnabled(true);
                        Log.e("tag", throwable.getMessage());
                        Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
