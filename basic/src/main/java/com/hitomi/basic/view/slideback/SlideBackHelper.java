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

    private SlideConfig slideConfig;
    private ActivityManager activityManager;
    private OnSlideListener onSlideListener;

    private SlideBackHelper() {
    }

    public static SlideBackHelper getInstance() {
        return SlideBackHelper.SingletonHolder.instance;
    }

    private ViewGroup getDecorView(Activity activity) {
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    private Drawable getDecorViewDrawable(Activity activity) {
        return getDecorView(activity).getBackground();
    }

    private View getContentView(Activity activity) {
        return getDecorView(activity).getChildAt(0);
    }

    public void setOnSlideListener(@Nullable OnSlideListener listener) {
        onSlideListener = listener;
    }

    public void init(@NonNull ActivityManager manager, @Nullable SlideConfig config) {
        activityManager = manager;
        slideConfig = config;
    }

    /**
     * 启动当前页面侧滑功能
     */
    public void startup() {
        if (activityManager == null || activityManager.getPreActivity() == null)
            return;

        final Activity currActivity = activityManager.getCurrentActivity();
        final ViewGroup decorView = getDecorView(currActivity);
        final View contentView = decorView.getChildAt(0);
        decorView.removeViewAt(0);

        View content = contentView.findViewById(android.R.id.content);
        if (content.getBackground() == null) {
            content.setBackgroundDrawable(decorView.getBackground());
        }

        final Activity[] preActivity = {activityManager.getPreActivity()};
        final View[] preContentView = {getContentView(preActivity[0])};

        Drawable preDecorViewDrawable = getDecorViewDrawable(preActivity[0]);
        content = preContentView[0].findViewById(android.R.id.content);
        if (content.getBackground() == null) {
            content.setBackgroundDrawable(preDecorViewDrawable);
        }

        final SlideBackLayout slideBackLayout = new SlideBackLayout(currActivity, contentView, preContentView[0],
                preDecorViewDrawable, slideConfig, new OnInternalStateListener() {

            @Override
            public void onSlide(float percent) {
                if (onSlideListener != null) {
                    onSlideListener.onSlide(percent);
                }
            }

            @Override
            public void onOpen() {
                if (onSlideListener != null) {
                    onSlideListener.onOpen();
                }
            }

            @Override
            public void onClose(Boolean finishActivity) {

                // finishActivity为true时关闭页面，为false时不关闭页面，为null时为其他地方关闭页面时调用SlideBackLayout.isComingToFinish的回调
                if (onSlideListener != null) {
                    onSlideListener.onClose();
                }

                if ((finishActivity == null || !finishActivity) && onSlideListener != null) {
                    onSlideListener.onClose();
                }

                if (slideConfig != null && slideConfig.isRotateScreen()) {

                    if (finishActivity != null && finishActivity) {
                        contentView.setVisibility(View.INVISIBLE);
                    }

                    if (preActivity[0] != null && preContentView[0].getParent() != getDecorView(preActivity[0])) {
                        preContentView[0].setX(0);
                        ((ViewGroup) preContentView[0].getParent()).removeView(preContentView[0]);
                        getDecorView(preActivity[0]).addView(preContentView[0], 0);
                    }
                }

                if (finishActivity != null && finishActivity) {
                    finishActivityByNoAnimation(currActivity);
                } else if (finishActivity == null) {
                    activityManager.removeActivity(currActivity);
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
                if (activity != preActivity[0]) {
                    preActivity[0] = activity;
                    preContentView[0] = getContentView(preActivity[0]);
                    slideBackLayout.updatePreContentView(preContentView[0]);
                }
            }

        });

        decorView.addView(slideBackLayout);
    }

    private static class SingletonHolder {
        final static SlideBackHelper instance = new SlideBackHelper();
    }

}
