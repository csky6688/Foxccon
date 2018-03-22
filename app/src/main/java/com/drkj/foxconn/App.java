package com.drkj.foxconn;

import android.app.Application;

import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.util.FileUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by ganlong on 2018/1/30.
 */

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DbController.getInstance();
        initFile();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initFile() {
        File file = new File(FileUtil.FOXCONN_DIR);
        file.mkdirs();
    }

    public static App getInstance() {
        return instance;
    }
}
