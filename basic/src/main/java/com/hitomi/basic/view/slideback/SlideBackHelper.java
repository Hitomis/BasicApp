package com.hitomi.basic.view.slideback;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hitomi.basic.manager.ActivityManager;
import com.hitomi.basic.view.slideback.callbak.OnInternalStateListener;
import com.hitomi.basic.view.slideback.callbak.OnSlideListener;
import com.hitomi.basic.view.slideback.widget.SlideBackLayout;


public class SlideBackHelper {

    private Activity preActivity;
    private View preContentView;

    private SlideBackHelper() {
    }

    private static class SingletonHolder {
        final static SlideBackHelper instance = new SlideBackHelper();
    }

    public static SlideBackHelper getInstance() {
        return SlideBackHelper.SingletonHolder.instance;
    }

    public ViewGroup getDecorView(Activity activity) {
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    public Drawable getDecorViewDrawable(Activity activity) {
        return getDecorView(activity).getBackground();
    }

    public View getContentView(Activity activity) {
        return getDecorView(activity).getChildAt(0);
    }

    /**
     * 附着Activity，实现侧滑
     *
     * @param curActivity 当前Activity
     * @param config      参数配置
     * @param listener    滑动的监听
     * @return 处理侧滑的布局，提高方法动态设置滑动相关参数
     */
    public void attach(@NonNull final Activity curActivity, @Nullable final SlideConfig config, @Nullable final OnSlideListener listener) {

        final ActivityManager activityManager = ActivityManager.getInstance();
        if (activityManager.getPreActivity() == null) return ;

        final ViewGroup decorView = getDecorView(curActivity);
        final View contentView = decorView.getChildAt(0);
        decorView.removeViewAt(0);

        View content = contentView.findViewById(android.R.id.content);
        if (content.getBackground() == null) {
            content.setBackgroundDrawable(decorView.getBackground());
        }


        preActivity = activityManager.getPreActivity();
        preContentView = getContentView(preActivity);
        Drawable preDecorViewDrawable = getDecorViewDrawable(preActivity);
        content = preContentView.findViewById(android.R.id.content);
        if (content.getBackground() == null) {
            content.setBackgroundDrawable(preDecorViewDrawable);
        }

        final SlideBackLayout slideBackLayout;
        slideBackLayout = new SlideBackLayout(curActivity, contentView, preContentView, preDecorViewDrawable, config, new OnInternalStateListener() {

            @Override
            public void onSlide(float percent) {
                if (listener != null) {
                    listener.onSlide(percent);
                }
            }

            @Override
            public void onOpen() {
                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override
            public void onClose(Boolean finishActivity) {

                // finishActivity为true时关闭页面，为false时不关闭页面，为null时为其他地方关闭页面时调用SlideBackLayout.isComingToFinish的回调
                if (listener != null) {
                    listener.onClose();
                }

                if ((finishActivity == null || !finishActivity) && listener != null) {
                    listener.onClose();
                }

                if (config != null && config.isRotateScreen()) {

                    if (finishActivity != null && finishActivity) {
                        contentView.setVisibility(View.INVISIBLE);
                    }

                    if (preActivity != null && preContentView.getParent() != getDecorView(preActivity)) {
                        preContentView.setX(0);
                        ((ViewGroup) preContentView.getParent()).removeView(preContentView);
                        getDecorView(preActivity).addView(preContentView, 0);
                    }
                }

                if (finishActivity != null && finishActivity) {
                    finishActivityByNoAnimation(curActivity);
                    activityManager.removeActivity(curActivity);
                } else if (finishActivity == null) {
                    activityManager.removeActivity(curActivity);
                }
            }

            /**
             * 无任何痕迹实现 Activity 关闭
             * @param activity
             */
            private void finishActivityByNoAnimation(Activity activity) {
                Window window = activity.getWindow();
                window.getDecorView().setAlpha(0);
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onCheckPreActivity(SlideBackLayout slideBackLayout) {
                Activity activity = activityManager.getPreActivity();
                if (activity != preActivity) {
                    preActivity = activity;
                    preContentView = getContentView(preActivity);
                    slideBackLayout.updatePreContentView(preContentView);
                }
            }

        });

        decorView.addView(slideBackLayout);
    }

}
