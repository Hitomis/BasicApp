package com.hitomi.basic.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Activity 栈管理帮助类 {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH} 以上可用 <br/>
 *
 * github : https://github.com/Hitomis <br/>
 *
 * Created by hitomi on 2016/10/11.
 */
public class ActivityManager implements Application.ActivityLifecycleCallbacks {
    private Stack<Activity> activityStack;
    private Set<String> ignoreActivityNames;

    public void init(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    private ActivityManager() {}

    private static class SingletonHolder {
        final static ActivityManager instance = new ActivityManager();
    }

    public static ActivityManager getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        for (String actName : ignoreActivityNames) {
            // 在 ignoreActivityNames 清单中的 Activity 不能被添加到管理器中
            if (actName.equals(activity.getClass().getSimpleName()))
                return ;
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        Activity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    public Activity getPreActivity() {
        int size = activityStack.size();
        if (size < 2) return null;
        return activityStack.get(size - 2);
    }

    /**
     * 获取当前 Activity 之前所有 Activity
     *
     * @return
     */
    public List<Activity> getPreActivies() {
        List<Activity> preActivities = new ArrayList<>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i) == getCurrentActivity()) {
                break;
            }
            preActivities.add(activityStack.get(i));
        }
//        activityStack.subList(from, to); 这个方法有毒，巨坑爹
        return preActivities;
    }

    /**
     * 获取现有的所有 Activity
     * @return
     */
    public List<Activity> getAllActivities() {
        return new ArrayList<>(activityStack);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.get(activityStack.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.get(i);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void removeAllWithoutItself(Activity activity) {
        activityStack.clear();
        addActivity(activity);
    }

    public ActivityManager addIgnore(String activityName) {
        if (ignoreActivityNames == null)
            ignoreActivityNames = new HashSet<>();

        if (activityName != null && activityName.length() != 0)
            ignoreActivityNames.add(activityName);
        return this;
    }

}
