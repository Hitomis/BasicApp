package com.hitomi.basic.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.hitomi.basic.manager.NetworkManager;
import com.hitomi.basic.manager.hook.HookListenerContract;
import com.hitomi.basic.manager.hook.HookManager;
import com.hitomi.basic.manager.hook.ListenerManager;
import com.hitomi.basic.model.AppExitEvent;
import com.hitomi.basic.net.OkHttpUtils;
import com.hitomi.basic.view.slideback.SlideBackHelper;

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
        }
        SlideBackHelper.getInstance().startup();
        observeNetwork();
    }

    /**
     * 观察网络变化
     */
    private void observeNetwork() {
        // 整个 app 生命周期中 OnNetworkStatusChangeListener 只能存在一个
        if (NetworkManager.getInstance().getNetworkChangeListener() == null) {
            NetworkManager.OnNetworkStatusChangeListener onNetworkStatusChangeListener = new NetworkManager.OnNetworkStatusChangeListener() {
                @Override
                public void onWifiAvailable() {
                    endHook();
                }

                @Override
                public void onMobileNetAvailable() {
                    endHook();
                }

                @Override
                public void onNetworkUnavailable() {
                    startHook();
                }
            };
            NetworkManager.getInstance().setNetworkChangeListener(onNetworkStatusChangeListener);
        }
    }

    /**
     * 开启 View 监听器相关的钩子函数, 修改全局状态
     */
    private void startHook() {
        ListenerManager.Builer builer = new ListenerManager.Builer().buildOnClickListener(
                new HookListenerContract.OnClickListener() {
                    @Override
                    public boolean doInListener(View v) {
                        log.e("当前没有网络");
                        return true;
                    }
                });
        HookManager.getInstance().startHook(this, ListenerManager.create(builer));
    }

    /**
     * 关闭钩子函数, 还原 View 监听器相关事件方法
     */
    private void endHook() {
        HookManager.getInstance().endHook();
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
        super.onDestroy(); // 销毁或反注册资源, 预防 OOM
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
