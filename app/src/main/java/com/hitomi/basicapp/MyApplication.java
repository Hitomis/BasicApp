package com.hitomi.basicapp;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.hitomi.basic.manager.ActivityManager;
import com.hitomi.basic.manager.NetworkManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hitomi on 2016/12/11.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        initXLog();
        ActivityManager.getInstance().init(this);
        NetworkManager.getInstance().init(this);
    }

    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG
                        ? LogLevel.DEBUG
                        : LogLevel.NONE) // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                .t()  // 允许打印线程信息，默认禁止
                .b()  // 允许打印日志边框，默认禁止
                .build();
        XLog.init(config, new AndroidPrinter());
    }
}
