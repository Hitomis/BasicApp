package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

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
        controller.prepareContentView();
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

        public Builder setLeftIcon(int leftIcon) {
            params.leftIcon = leftIcon;
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

        public Builder setRightIcon(int rightIcon) {
            params.rightIcon = rightIcon;
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

        public Builder setLeftOnclickListener(OnClickListener listener) {
            params.onLeftClickListener = listener;
            return this;
        }

        public Builder setRightOnclickListener(OnClickListener listener) {
            params.onRightClickListener = listener;
            return this;
        }

        public TitleBarLayout create() {
            final TitleBarLayout titleBar = new TitleBarLayout(params.context);
            params.apply(titleBar.controller);
            return titleBar;
        }

        public void setup(TitleBarLayout titleBar){
            params.apply(titleBar.controller);
        }
    }


}
