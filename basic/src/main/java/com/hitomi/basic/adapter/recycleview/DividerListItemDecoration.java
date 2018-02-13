package com.hitomi.basic.adapter.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView 列表分割线组件，适用于线性列表或者九宫格
 * Created by hitomi on 2016/12/2.
 */
public class DividerListItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 横向列表分割线
     */
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    /**
     * 众向列表分割线
     */
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;

    private int mOrientation;
    private int startPadding;
    private int endPadding;

    public DividerListItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public DividerListItemDecoration(Drawable divider, int orientation) {
        mDivider = divider;
        setOrientation(orientation);
    }

    public DividerListItemDecoration(Context context, int dividerRes, int orientation) {
        mDivider = context.getResources().getDrawable(dividerRes);
        setOrientation(orientation);
    }

    public DividerListItemDecoration(int color, int width, int height, int orientation) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setSize(width, height);
        mDivider = drawable;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setDividerDrawable(Drawable divider) {
        mDivider = divider;
    }

    public void setStartPadding(int startPadding) {
        this.startPadding = startPadding;
    }

    public void setEndPadding(int endPadding) {
        this.endPadding = endPadding;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {

        if (mOrientation == (VERTICAL_LIST | HORIZONTAL_LIST)) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        } else if (mOrientation == HORIZONTAL_LIST) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + startPadding;
        final int right = parent.getWidth() - parent.getPaddingRight() - endPadding;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + startPadding;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - endPadding;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

}
