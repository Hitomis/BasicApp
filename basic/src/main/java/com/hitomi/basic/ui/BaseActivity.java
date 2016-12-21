package com.hitomi.basic.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.hitomi.basic.manager.NetworkManager;
import com.hitomi.basic.model.AppExitEvent;
import com.hitomi.basic.net.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by hitomi on 2016/12/11.
 */
public abstract class BaseActivity extends AppCompatActivity implements UIHandler {

    protected Logger log = XLog.tag(this.getClass().getSimpleName()).build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getContentViewID() > 0) {
            setContentView(getContentViewID());
            initView();
            setViewListener();
            dealLogic(savedInstanceState);
            observeNetwork();
        }
    }

    private void observeNetwork() {
        NetworkManager.getInstance().setNetworkChangeListener(new NetworkManager.OnNetworkStatusChangeListener() {
            @Override
            public void onWifiAvailable() {

            }

            @Override
            public void onMobileNetAvailable() {

            }

            @Override
            public void onNetworkUnavailable() {

            }
        });
    }

    /**
     * 退出 app
     */
    protected void postExitApp() {
        EventBus.getDefault().post(new AppExitEvent());
    }

    /**
     * 退出 app 功能 需要使用 EventBus 通知每一个 Activity 执行 finish 方法.<br/>
     * 注意：若需要退出 app, 请使用 {@link BaseActivity#postExitApp()}. 不要调用该方法
     * @param event
     */
    @Subscribe
    public void exitApp(AppExitEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        NetworkManager.getInstance().destroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }


}
