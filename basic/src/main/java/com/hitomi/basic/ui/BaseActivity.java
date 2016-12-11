package com.hitomi.basic.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hitomi.basic.model.AppExitEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by hitomi on 2016/12/11.
 */
public abstract class BaseActivity extends AppCompatActivity implements UIHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getContentViewID() > 0) {
            setContentView(getContentViewID());
            initView();
            setViewListener();
            dealLogic(savedInstanceState);
        }
    }

    protected void PostExitApp() {
        EventBus.getDefault().post(new AppExitEvent());
    }

    @Subscribe
    public void exitApp(AppExitEvent event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
