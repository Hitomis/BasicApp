package com.hitomi.basicapp;

import android.app.Application;
import android.content.Context;

import com.hitomi.basic.manager.ActivityManager;
import com.hitomi.basic.manager.NetworkManager;
import com.hitomi.basic.manager.cache.CacheManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by hitomi on 2016/12/11.
 */
public class MyApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        initActivityManager();
        initXLog();
        CacheManager.init(this);
        NetworkManager.getInstance().init(this);
    }

    /**
     * 初始化 Activity 栈管理器
     */
    private void initActivityManager() {
        ActivityManager activityManager = ActivityManager.getInstance();
        activityManager.init(this);
        // 不管理 LeakCanary 用于显示 OOM 信息的 Activity
        activityManager.addIgnore("DisplayLeakActivity");
    }

    /**
     * 初始化 logger 日志管理器
     */
    private void initXLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("Basic")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
