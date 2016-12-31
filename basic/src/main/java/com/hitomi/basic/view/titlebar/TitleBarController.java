package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by hitomi on 2016/12/27.
 */

public class TitleBarController {

    private final Context context;
    private final TitleBarLayout titleBar;

    private TextView tvTitle, leftText, rightText;
    private ImageView leftImage, rightImage;

    public TitleBarController(Context context, TitleBarLayout titleBarLayout) {
        this.context = context;
        this.titleBar = titleBarLayout;
    }

    public void prepareContentView() {
        tvTitle = new TextView(context);
        RelativeLayout.LayoutParams titleRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        titleRlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        titleBar.addView(tvTitle, titleRlp);
    }

    private void setTitleText(CharSequence title) {
        if (TextUtils.isEmpty(title)) return ;
        tvTitle.setText(title);
    }

    private void setTitleColor(int color) {
        if (color == 0) return;
        tvTitle.setTextColor(color);
    }

    private void setTitleSize(float size) {
        if (size <= 0) return ;
        tvTitle.setTextSize(size);
    }

    private void setBarColor(int color) {
        if (color == 0) return ;
        titleBar.setBackgroundColor(color);
    }

    private void createLeftText(int leftRegion, int leftMargin) {
        if (leftText != null) return ;
        leftText = new TextView(context);
        leftText.setPadding(leftRegion, leftRegion, leftRegion, leftRegion);
        RelativeLayout.LayoutParams leftRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        leftRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        leftRlp.leftMargin = leftMargin;
        titleBar.addView(leftText, leftRlp);
    }

    private void setLeftText(CharSequence leftStr) {
        leftText.setText(leftStr);
    }

    private void setLeftTextColor(int leftColor) {
        if (leftColor == 0) return ;
        leftText.setTextColor(leftColor);
    }

    private void setLeftTextSize(float leftSize) {
        if (leftSize <= 0) return ;
        leftText.setTextSize(leftSize);
    }

    private void createLeftIcon(int leftRegion, int leftMargin) {
        if (leftImage != null) return;
        leftImage = new ImageView(context);
        leftImage.setPadding(leftRegion, leftRegion, leftRegion, leftRegion);
        RelativeLayout.LayoutParams leftRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        leftRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        leftRlp.leftMargin = leftMargin;
        titleBar.addView(leftImage, leftRlp);
    }

    private void setLeftIcon(int leftIcon) {
        if (leftIcon == 0) return ;
        leftImage.setImageResource(leftIcon);
    }

    private void setLeftOnClickListener(View.OnClickListener onLeftClickListener) {
        if (leftText == null) {
            leftImage.setOnClickListener(onLeftClickListener);
        } else {
            leftText.setOnClickListener(onLeftClickListener);
        }
    }

    private void createRightText(int rightRegion, int rightMargin) {
        if (rightText != null) return ;
        rightText = new TextView(context);
        rightText.setPadding(rightRegion, rightRegion, rightRegion ,rightRegion);
        RelativeLayout.LayoutParams rightRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightRlp.rightMargin = rightMargin;
        titleBar.addView(rightText, rightRlp);
    }

    public void setRightText(CharSequence rightStr) {
        rightText.setText(rightStr);
    }

    public void setRightTextColor(int rightTextColor) {
        if (rightTextColor == 0) return ;
        rightText.setTextColor(rightTextColor);
    }

    public void setRightTextSize(float rightTextSize) {
        if (rightTextSize <= 0) return ;
        rightText.setTextSize(rightTextSize);
    }

    private void createRightIcon(int rightRegion, int rightMargin) {
        if (rightImage != null) return;
        rightImage = new ImageView(context);
        rightImage.setPadding(rightRegion, rightRegion, rightRegion, rightRegion);
        RelativeLayout.LayoutParams rightRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightRlp.rightMargin = rightMargin;
        titleBar.addView(rightImage, rightRlp);
    }

    public void setRightIcon(int rightIcon) {
        if (rightIcon == 0) return ;
        rightImage.setImageResource(rightIcon);
    }

    public void setRightOnClickListener(View.OnClickListener onRightClickListener) {
        if (rightText == null) {
            rightImage.setOnClickListener(onRightClickListener);
        } else {
            rightText.setOnClickListener(onRightClickListener);
        }
    }

    public static class TitleParams {
        public final Context context;

        public int barColor;

        public CharSequence title;
        public int titleColor;
        public float titleSize;

        public CharSequence leftText;
        public int leftTextColor;
        public float leftTextSize;
        public int leftIcon;
        public int leftMargin;
        public int leftRegion;

        public CharSequence rightText;
        public int rightTextColor;
        public float rightTextSize;
        public int rightIcon;
        public int rightMargin;
        public int rightRegion;

        public View.OnClickListener onLeftClickListener;
        public View.OnClickListener onRightClickListener;


        public TitleParams(Context context) {
            this.context = context;
        }

        public void apply(TitleBarController controller) {
            controller.setBarColor(barColor);
            controller.setTitleText(title);
            controller.setTitleColor(titleColor);
            controller.setTitleSize(titleSize);

            if (TextUtils.isEmpty(leftText)) {
                controller.createLeftIcon(leftRegion, leftMargin);
                controller.setLeftIcon(leftIcon);
            } else {
                controller.createLeftText(leftRegion, leftMargin);
                controller.setLeftText(leftText);
                controller.setLeftTextColor(leftTextColor);
                controller.setLeftTextSize(leftTextSize);
            }
            controller.setLeftOnClickListener(onLeftClickListener);

            if (TextUtils.isEmpty(rightText)) {
                controller.createRightIcon(rightRegion, rightMargin);
                controller.setRightIcon(rightIcon);
            } else {
                controller.createRightText(rightRegion, rightMargin);
                controller.setRightText(rightText);
                controller.setRightTextColor(rightTextColor);
                controller.setRightTextSize(rightTextSize);
            }
            controller.setRightOnClickListener(onRightClickListener);
        }
    }

}
