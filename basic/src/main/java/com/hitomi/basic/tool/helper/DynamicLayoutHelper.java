package com.hitomi.basic.tool.helper;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 动态监听 View 尺寸变化：专适用于解决带虚拟导航栏的手机导航栏
 * 隐藏或者消失时带来的布局UI错乱问题，例如 HTC/华为/三星s8
 * Created by zhaofan on 2017/8/5 0005.
 */
public class DynamicLayoutHelper {

    private View viewObserved;//被监听的视图
    private OnLayoutChangeListener layoutChangeListener;
    private int usableHeightPrevious;//视图变化前的可用高度

    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (viewObserved == null) return;
            resetLayoutByUsableWidth(computeUsableWidth());
        }
    };

    private DynamicLayoutHelper() {
    }

    private static class SingletonHolder {
        private final static DynamicLayoutHelper instance = new DynamicLayoutHelper();
    }

    public static DynamicLayoutHelper getInstance() {
        return SingletonHolder.instance;
    }

    public void assistView(View view, OnLayoutChangeListener listener) {
        viewObserved = view;
        layoutChangeListener = listener;
        //给View添加全局的布局监听器
        viewObserved.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    private void resetLayoutByUsableWidth(int usableWidthNow) {
        if (usableWidthNow != usableHeightPrevious) {
            if (layoutChangeListener != null)
                layoutChangeListener.onSizeChange();
            usableHeightPrevious = usableWidthNow;
        }
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private int computeUsableWidth() {
        Rect r = new Rect();
        viewObserved.getWindowVisibleDisplayFrame(r);
        return (r.right - r.left);
    }

    public interface OnLayoutChangeListener {
        void onSizeChange();
    }

    public void unassist() {
        if (viewObserved != null)
            viewObserved.getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
        viewObserved = null;
        layoutChangeListener = null;
    }

}
