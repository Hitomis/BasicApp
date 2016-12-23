package com.hitomi.basicapp;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.hitomi.basic.manager.ActivityManager;
import com.hitomi.basic.manager.NetworkManager;
import com.hitomi.basic.manager.cache.CacheManager;
import com.hitomi.basic.view.slideback.SlideBackHelper;
import com.hitomi.basic.view.slideback.SlideConfig;
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
        ActivityManager.getInstance().init(this);
        initXLog();
        CacheManager.init(this);
        NetworkManager.getInstance().init(this);
        initSlideback();
    }

    private void initSlideback() {
        SlideConfig slideConfig = new SlideConfig.Builder()
                .rotateScreen(false) // 屏幕是否旋转
                .edgeOnly(true) //  // 是否仅为侧边缘滑动，false: 任何地方都可以滑动
                .lock(false) // 是否禁止侧滑
                .edgePercent(0.1f) // 边缘滑动的响应阈值，0~1，对应屏幕宽度*percent
                .slideOutPercent(0.5f) // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                .create();
        SlideBackHelper.getInstance().init(slideConfig);
    }

    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG
                        ? LogLevel.DEBUG
                        : LogLevel.NONE) // DEBUG 模式下打印日志, RELEASE 不再打印日志
                .t()  // 允许打印线程信息，默认禁止
                .b()  // 允许打印日志边框，默认禁止
                .build();
        XLog.init(config, new AndroidPrinter());
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
