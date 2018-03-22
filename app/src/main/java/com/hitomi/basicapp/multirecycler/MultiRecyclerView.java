package com.hitomi.basicapp.multirecycler;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;


public class MultiRecyclerView extends RecyclerView implements View.OnClickListener {

    private static final String TAG = "MultiRecyclerView";

    private ItemViewMode viewMode;
    private View currentCenterChildView;

    private OnScrollListener onScrollListener;
    private OnCenterItemClickListener centerItemClickListener;
    private final CenterRunnable centerRunnable = new CenterRunnable();

    private boolean isForceCentering; // 是否在让 Item 居中的滚动动画中
    private boolean needCenterForce = true; // 是否始终让 Item 居中
    private boolean needFirstCenter = true; // 是否初始化时让第一个 Item 居中
    private boolean firstSetAdapter = true; // 是否第一次设置数据适配器
    private boolean clickSmoothCenter = true; // 是否允许点击不在中心的 Item 后，滚动到中心位置

    public MultiRecyclerView(Context context) {
        this(context, null);
    }

    public MultiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (needCenterForce) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (layoutManager.canScrollHorizontally())
                setPadding(getWidth() / 2, 0, getWidth() / 2, 0);
            else if (layoutManager.canScrollVertically())
                setPadding(0, getHeight() / 2, 0, getHeight() / 2);
            setClipToPadding(false);
            setClipChildren(false);
            currentCenterChildView = findViewAtCenter();
            if (needFirstCenter) {
                needFirstCenter = false;
                scrollToPositionDelayed(0, 20);
            }
            smoothScrollToView(currentCenterChildView);
        } else {
            setClipToPadding(false);
            setClipChildren(false);
        }

        if (currentCenterChildView != null)
            currentCenterChildView.setOnClickListener(this);
    }

    private void scrollToPositionDelayed(final int position, final int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollToPosition(position);
            }
        }, delay);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        bindItemClickListener(getChildCount());
        if (onScrollListener != null)
            onScrollListener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (onScrollListener != null)
            onScrollListener.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE) {
            if (needCenterForce && !isForceCentering) {
                isForceCentering = true;
                currentCenterChildView = findViewAtCenter();
                if (currentCenterChildView != null && centerItemClickListener != null)
                    currentCenterChildView.setOnClickListener(this);
                centerRunnable.setView(currentCenterChildView);
                ViewCompat.postOnAnimation(this, centerRunnable);
            }
        }

        if (onScrollListener != null)
            onScrollListener.onScrollStateChanged(state);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        removeCallbacks(centerRunnable);
        isForceCentering = false;
        return super.onTouchEvent(e);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        if (getLayoutManager() != null)
            bindItemClickListener(getLayoutManager().getChildCount());
    }

    private void bindItemClickListener(int count) {
        for (int i = 0; i < count; ++i) {
            View v = getChildAt(i);
            if (viewMode != null) viewMode.applyToView(v, this);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    View centerView = findViewAtCenter();
                    if (centerView == v) {
                        if (centerItemClickListener != null)
                            centerItemClickListener.onCenterItemClick(v);
                    } else {
                        if (clickSmoothCenter) smoothScrollToView(v);
                    }
                }
            });
        }
    }

    public void smoothScrollToView(View v) {
        int distance = 0;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            if (getLayoutManager().canScrollVertically()) {
                final float y = v.getY() + v.getHeight() * 0.5f;
                final float halfHeight = getHeight() * 0.5f;
                distance = (int) (y - halfHeight);
            } else if (getLayoutManager().canScrollHorizontally()) {
                final float x = v.getX() + v.getWidth() * 0.5f;
                final float halfWidth = getWidth() * 0.5f;
                distance = (int) (x - halfWidth);
            }

        } else
            throw new IllegalArgumentException("CircleRecyclerView just support T extend LinearLayoutManager!");
        smoothScrollBy(distance, distance);
    }

    public View findViewAt(int x, int y) {
        final int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            final View v = getChildAt(i);
            final int x0 = v.getLeft();
            final int y0 = v.getTop();
            final int x1 = v.getWidth() + x0;
            final int y1 = v.getHeight() + y0;
            if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
                return v;
            }
        }
        return null;
    }

    public View findViewAtCenter() {
        if (getLayoutManager().canScrollVertically()) {
            return findViewAt(0, getHeight() / 2);
        } else if (getLayoutManager().canScrollHorizontally()) {
            return findViewAt(getWidth() / 2, 0);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        if (centerItemClickListener != null)
            centerItemClickListener.onCenterItemClick(v);
    }

    public class CenterRunnable implements Runnable {

        private WeakReference<View> mView;

        public void setView(View v) {
            mView = new WeakReference<View>(v);
        }

        @Override
        public void run() {
            smoothScrollToView(mView.get());
            if (needCenterForce)
                isForceCentering = true;
        }
    }

    public void setNeedFirstCenter(boolean needFirstCenter) {
        this.needFirstCenter = needFirstCenter;
    }

    public void setNeedCenterForce(boolean needCenterForce) {
        this.needCenterForce = needCenterForce;
    }

    /**
     * set the center item clickListener
     *
     * @param listener
     */
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        centerItemClickListener = listener;
    }


    public void setViewMode(ItemViewMode mode) {
        viewMode = mode;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    public interface OnCenterItemClickListener {
        void onCenterItemClick(View v);
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);

        void onScrollStateChanged(int state);

        void onScrolled(int dx, int dy);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (firstSetAdapter) {
            firstSetAdapter = false;
        } else {
            if (adapter != null && needCenterForce)
                scrollToPositionDelayed(0, 0);
        }
    }
}
