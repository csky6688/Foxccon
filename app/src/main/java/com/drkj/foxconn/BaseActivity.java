package com.drkj.foxconn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.drkj.foxconn.util.NfcCardUtil;
import com.zltd.decoder.DecoderManager;

/**
 * Created by ganlong on 2018/1/16.
 * Modify by VeronicaRen on 2018/3/14.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected NfcCardUtil nfcCardUtil;

    protected boolean isResume = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcCardUtil = new NfcCardUtil(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        nfcCardUtil.startNfc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
        nfcCardUtil.stopNfc();
    }
}
