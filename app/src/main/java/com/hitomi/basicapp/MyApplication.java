package com.hitomi.basicapp;

import android.app.Application;

import com.hitomi.basic.manager.ActivityManager;

/**
 * Created by hitomi on 2016/12/11.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(ActivityManager.getInstance());
    }
}
