package com.drkj.foxconn.function;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by VeronicaRen on 2018/3/29 in Java
 */
public class CrashHandler implements UncaughtExceptionHandler {

//    public static String

    private UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;

    private static CrashHandler instance = new CrashHandler();

    private Map<String, String> info = new HashMap<>();//设备异常信息

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
//                    Toast.makeText(mContext,)
                    Looper.loop();
                }
            }).start();

        } catch (Exception e) {

        }
        return true;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (Exception e) {

        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                info.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
//                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
}
