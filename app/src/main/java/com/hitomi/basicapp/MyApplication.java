package com.hitomi.basicapp;

import android.app.Application;

import com.hitomi.basic.manager.ActivityManager;
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
        registerActivityLifecycleCallbacks(ActivityManager.getInstance());

    }
}
