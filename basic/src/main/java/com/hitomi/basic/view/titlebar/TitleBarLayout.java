package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hitomi on 2016/12/27.
 */
public class TitleBarLayout extends RelativeLayout {

    TitleBarController controller;

    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        controller = new TitleBarController(context, this);
    }

    public static class Builder {
        private final TitleBarController.TitleParams params;

        public Builder(Context context) {
            params = new TitleBarController.TitleParams(context);
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setTitle(String title, int titleColor, float titleSize) {
            params.title = title;
            params.titleColor = titleColor;
            params.titleSize = titleSize;
            return this;
        }

        public Builder setTitleView(TextView tvTitle) {
            params.tvTitle = tvTitle;
            return this;
        }

        public Builder setTitleView(TextView tvTitle, RelativeLayout.LayoutParams titleRlp) {
            params.tvTitle = tvTitle;
            params.titleRlp = titleRlp;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            params.titleColor = titleColor;
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            params.titleSize = titleSize;
            return this;
        }

        public Builder setBarColor(int barColor) {
            params.barColor = barColor;
            return this;
        }

        public Builder setLeftText(String leftText) {
            params.leftText = leftText;
            return this;
        }

        public Builder setLeftTextColor(int leftTextColor) {
            params.leftTextColor = leftTextColor;
            return this;
        }

        public Builder setLeftTextSize(int leftTextSize) {
            params.leftTextSize = leftTextSize;
            return this;
        }

        public Builder setLeftText(String leftText, int leftTextColor, int leftTextSize) {
            params.leftText = leftText;
            params.leftTextColor = leftTextColor;
            params.leftTextSize = leftTextSize;
            return this;
        }

        public Builder setLeftTextView(TextView leftTextView) {
            params.tvleft = leftTextView;
            return this;
        }

        public Builder setLeftIcon(int leftIcon) {
            params.leftIcon = leftIcon;
            return this;
        }

        public Builder setLeftBitmap(Bitmap leftBitmap) {
            params.leftBitmap = leftBitmap;
            return this;
        }

        public Builder setLeftIcon(int leftIcon, int leftImageSize) {
            params.leftIcon = leftIcon;
            params.leftImageSize = leftImageSize;
            return this;
        }

        public Builder setLeftBitmap(Bitmap leftBitmap, int leftImageSize) {
            params.leftBitmap = leftBitmap;
            params.leftImageSize = leftImageSize;
            return this;
        }

        public Builder setLeftImageSize(int leftImageSize) {
            params.leftImageSize = leftImageSize;
            return this;
        }

        public Builder setLeftMargin(int leftMargin) {
            params.leftMargin = leftMargin;
            return this;
        }

        public Builder setLeftRegion(int leftRegion) {
            params.leftRegion = leftRegion;
            return this;
        }

        public Builder setRightText(String rightText) {
            params.rightText = rightText;
            return this;
        }

        public Builder setRightTextColor(int rightTextColor) {
            params.rightTextColor = rightTextColor;
            return this;
        }

        public Builder setRightTextSize(int rightTextSize) {
            params.rightTextSize = rightTextSize;
            return this;
        }

        public Builder setRightText(String rightText, int rightTextColor, int rightTextSize) {
            params.rightText = rightText;
            params.rightTextColor = rightTextColor;
            params.rightTextSize = rightTextSize;
            return this;
        }

        public Builder setRightTextView(TextView rightTextView) {
            params.tvRight = rightTextView;
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            params.rightIcon = rightIcon;
            return this;
        }

        public Builder setRightBitmap(Bitmap rightBitmap) {
            params.rightBitmap = rightBitmap;
            return this;
        }

        public Builder setRightIcon(int rightIcon, int rightImageSize) {
            params.rightIcon = rightIcon;
            params.rightImageSize = rightImageSize;
            return this;
        }

        public Builder setRightBitmap(Bitmap rightBitmap, int rightImageSize) {
            params.rightBitmap = rightBitmap;
            params.rightImageSize = rightImageSize;
            return this;
        }

        public Builder setRightImageSize(int rightImageSize) {
            params.rightImageSize = rightImageSize;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            params.rightMargin = rightMargin;
            return this;
        }

        public Builder setRightRegion(int rightRegion) {
            params.rightRegion = rightRegion;
            return this;
        }

        public Builder setRightSubText(String rightSubText) {
            params.rightSubText = rightSubText;
            return this;
        }

        public Builder setRightSubTextColor(int rightSubTextColor) {
            params.rightSubTextColor = rightSubTextColor;
            return this;
        }

        public Builder setRightSubTextSize(int rightSubTextSize) {
            params.rightSubTextSize = rightSubTextSize;
            return this;
        }

        public Builder setRightSubText(String rightSubText, int rightSubTextColor, int rightSubTextSize) {
            params.rightSubText = rightSubText;
            params.rightSubTextColor = rightSubTextColor;
            params.rightSubTextSize = rightSubTextSize;
            return this;
        }

        public Builder setRightSubIcon(int rightSubIcon) {
            params.rightSubIcon = rightSubIcon;
            return this;
        }

        public Builder setRightSubBitmap(Bitmap rightSubBitmap) {
            params.rightSubBitmap = rightSubBitmap;
            return this;
        }

        public Builder setRightSubIcon(int rightSubIcon, int rightSubImageSize) {
            params.rightSubIcon = rightSubIcon;
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        public Builder setRightSubBitmap(Bitmap rightSubBitmap, int rightSubImageSize) {
            params.rightSubBitmap = rightSubBitmap;
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        public Builder setRightSubImageSize(int rightSubImageSize) {
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        public Builder setRightSubMargin(int rightSubMargin) {
            params.rightSubMargin = rightSubMargin;
            return this;
        }

        public Builder setRightSubRegion(int rightSubRegion) {
            params.rightSubRegion = rightSubRegion;
            return this;
        }

        public Builder setTitleOnClickListener(OnClickListener listener) {
            params.onTitleClickListener = listener;
            return this;
        }

        public Builder setLeftOnClickListener(OnClickListener listener) {
            params.onLeftClickListener = listener;
            return this;
        }

        public Builder setRightOnClickListener(OnClickListener listener) {
            params.onRightClickListener = listener;
            return this;
        }

        public Builder setRightSubOnClickListener(OnClickListener listener) {
            params.onRightSubClickListener = listener;
            return this;
        }

        public TitleBarLayout create() {
            final TitleBarLayout titleBar = new TitleBarLayout(params.context);
            params.apply(titleBar.controller);
            return titleBar;
        }

        public void setup(TitleBarLayout titleBar) {
            params.apply(titleBar.controller);
        }
    }
}
