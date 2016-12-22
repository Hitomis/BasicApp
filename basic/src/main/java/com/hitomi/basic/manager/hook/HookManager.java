package com.hitomi.basic.manager.hook;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HookManager {
    private Activity activity;
    private ListenerManager mListenerManager;
    private boolean startHook;

    private HookManager() {
    }

    private static class SingleHolder {
        public static final HookManager INSTANCE = new HookManager();
    }

    public static HookManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 开启 View 监听器中的钩子函数
     *
     * @param activity
     */
    public void startHook(Activity activity, ListenerManager listenerManager) {
        startHook = true;
        this.activity = activity;
        this.mListenerManager = listenerManager;

        List<View> views = getAllChildViews(activity);
        for (View v : views) {
            hookSingleView(v);
        }
    }

    /**
     * 关闭 View 监听器中的钩子函数
     */
    public void endHook() {
        if (!startHook) return;
        mListenerManager = null;

        List<View> views = getAllChildViews(activity);
        for (View v : views) {
            unHookSingleView(v);
        }

    }

    private void unHookSingleView(View v) {
        Class mClassView;
        try {
            mClassView = Class.forName("android.view.View");
            Method method = mClassView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObject = method.invoke(v);

            Class mClassListenerInfo = Class.forName("android.view.View$ListenerInfo");

            Field feildOnClickListener = mClassListenerInfo.getDeclaredField("mOnClickListener");
            feildOnClickListener.setAccessible(true);
            Object mOnclickListener = feildOnClickListener.get(listenerInfoObject);
            if (mOnclickListener instanceof OnClickListenerProxy) {
                OnClickListenerProxy onClickListenerProxy = (OnClickListenerProxy) mOnclickListener;
                feildOnClickListener.set(listenerInfoObject, onClickListenerProxy.getOriginOnClickListener());
            }

            Field feildOnLongClickListener = mClassListenerInfo.getDeclaredField("mOnLongClickListener");
            feildOnLongClickListener.setAccessible(true);
            Object mOnLongClickListener = feildOnLongClickListener.get(listenerInfoObject);
            if (mOnLongClickListener instanceof OnLongClickListenerProxy) {
                OnLongClickListenerProxy onLongClickListenerProxy = (OnLongClickListenerProxy) mOnLongClickListener;
                feildOnLongClickListener.set(listenerInfoObject, onLongClickListenerProxy.getOriginLongClickListener());
            }

            Field feildOnFocusChangeListener = mClassListenerInfo.getDeclaredField("mOnFocusChangeListener");
            feildOnFocusChangeListener.setAccessible(true);
            Object mOnFocusChangeListener = feildOnFocusChangeListener.get(listenerInfoObject);
            if (mOnFocusChangeListener instanceof OnFocusChangeListenerProxy) {
                OnFocusChangeListenerProxy onFocusChangeListenerProxy = (OnFocusChangeListenerProxy) mOnFocusChangeListener;
                feildOnFocusChangeListener.set(listenerInfoObject, onFocusChangeListenerProxy.getOriginFocusChangeListener());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hook 单个view
     *
     * @param view
     */
    private void hookSingleView(View view) {
        Class mClassView;
        try {
            mClassView = Class.forName("android.view.View");
            Method method = mClassView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObject = method.invoke(view);

            Class mClassListenerInfo = Class.forName("android.view.View$ListenerInfo");

            Field feildOnClickListener = mClassListenerInfo.getDeclaredField("mOnClickListener");
            feildOnClickListener.setAccessible(true);
            View.OnClickListener mOnClickListenerObject = (View.OnClickListener) feildOnClickListener.get(listenerInfoObject);

            Field feildOnLongClickListener = mClassListenerInfo.getDeclaredField("mOnLongClickListener");
            feildOnLongClickListener.setAccessible(true);
            View.OnLongClickListener mOnLongClickListenerObject = (View.OnLongClickListener) feildOnLongClickListener.get(listenerInfoObject);

            Field feildOnFocusChangeListener = mClassListenerInfo.getDeclaredField("mOnFocusChangeListener");
            feildOnFocusChangeListener.setAccessible(true);
            View.OnFocusChangeListener mOnFocusChangeListenerObject = (View.OnFocusChangeListener) feildOnFocusChangeListener.get(listenerInfoObject);

            View.OnClickListener onClickListenerProxy = new OnClickListenerProxy(mOnClickListenerObject, mListenerManager.mOnClickListener);
            View.OnLongClickListener onLongClickListenerProxy = new OnLongClickListenerProxy(mOnLongClickListenerObject, mListenerManager.mOnLongClickListener);
            View.OnFocusChangeListener onFocusChangeListenerProxy = new OnFocusChangeListenerProxy(mOnFocusChangeListenerObject, mListenerManager.mOnFocusChangeListener);

            feildOnClickListener.set(listenerInfoObject, onClickListenerProxy);
            feildOnLongClickListener.set(listenerInfoObject, onLongClickListenerProxy);
            feildOnFocusChangeListener.set(listenerInfoObject, onFocusChangeListenerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历需要监听Listenerd的Activity
     *
     * @param activity
     * @return
     */
    private List<View> getAllChildViews(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return getAllChildViews(view);
    }

    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }
}
