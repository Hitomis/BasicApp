package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.support.annotation.IdRes;
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

    @IdRes static final int ID_RIGHT = 10001;

    private final Context context;
    private final TitleBarLayout titleBar;

    private TextView tvTitle, leftText, rightText, rightSubText;
    private ImageView leftImage, rightImage, rightSubImage;

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

    private void createLeftIcon(int leftIconSize, int leftRegion, int leftMargin) {
        if (leftImage != null) return;
        leftImage = new ImageView(context);
        leftImage.setPadding(leftRegion, leftRegion, leftRegion, leftRegion);
        int iconSize = leftIconSize == 0 ? WRAP_CONTENT : leftIconSize;
        RelativeLayout.LayoutParams leftRlp = new RelativeLayout.LayoutParams(iconSize, iconSize);
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
        rightText.setId(ID_RIGHT);
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

    private void createRightIcon(int rightIconSize, int rightRegion, int rightMargin) {
        if (rightImage != null) return;
        rightImage = new ImageView(context);
        rightImage.setId(ID_RIGHT);
        rightImage.setPadding(rightRegion, rightRegion, rightRegion, rightRegion);
        int iconSize = rightIconSize == 0 ? WRAP_CONTENT : rightIconSize;
        RelativeLayout.LayoutParams rightRlp = new RelativeLayout.LayoutParams(iconSize, iconSize);
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

    private void createRightSubText(int rightSubRegion, int rightSubMargin) {
        if (rightSubText != null) return ;
        rightSubText = new TextView(context);
        rightSubText.setPadding(rightSubRegion, rightSubRegion, rightSubRegion ,rightSubRegion);
        RelativeLayout.LayoutParams rightSubRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rightSubRlp.addRule(RelativeLayout.LEFT_OF, ID_RIGHT);
        rightSubRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightSubRlp.rightMargin = rightSubMargin;
        titleBar.addView(rightSubText, rightSubRlp);
    }

    private void setRightSubText(CharSequence rightSubStr) {
        rightSubText.setText(rightSubStr);
    }

    private void setRightSubTextColor(int rightSubTextColor) {
        if (rightSubTextColor == 0) return ;
        rightSubText.setTextColor(rightSubTextColor);
    }

    private void setRightSubTextSize(float rightSubTextSize) {
        if (rightSubTextSize <= 0) return ;
        rightSubText.setTextSize(rightSubTextSize);
    }

    private void createRightSubIcon(int rightSubIconSize, int rightSubRegion, int rightSubMargin) {
        if (rightSubImage != null) return;
        rightSubImage = new ImageView(context);
        rightSubImage.setPadding(rightSubRegion, rightSubRegion, rightSubRegion, rightSubRegion);
        int iconSize = rightSubIconSize == 0 ? WRAP_CONTENT : rightSubIconSize;
        RelativeLayout.LayoutParams rightSubRlp = new RelativeLayout.LayoutParams(iconSize, iconSize);
        rightSubRlp.addRule(RelativeLayout.LEFT_OF, ID_RIGHT);
        rightSubRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightSubRlp.rightMargin = rightSubMargin;
        titleBar.addView(rightSubImage, rightSubRlp);
    }

    private void setRightSubIcon(int rightSubIcon) {
        if (rightSubIcon == 0) return ;
        rightSubImage.setImageResource(rightSubIcon);
    }

    private void setRightSubOnClickListener(View.OnClickListener onRightSubClickListener) {
        if (rightSubText == null) {
            rightSubImage.setOnClickListener(onRightSubClickListener);
        } else {
            rightSubText.setOnClickListener(onRightSubClickListener);
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
        public int leftIconSize;
        public int leftMargin;
        public int leftRegion;

        public CharSequence rightText;
        public int rightTextColor;
        public float rightTextSize;
        public int rightIcon;
        public int rightIconSize;
        public int rightMargin;
        public int rightRegion;

        public CharSequence rightSubText;
        public int rightSubTextColor;
        public float rightSubTextSize;
        public int rightSubIcon;
        public int rightSubIconSize;
        public int rightSubMargin;
        public int rightSubRegion;

        public View.OnClickListener onLeftClickListener;
        public View.OnClickListener onRightClickListener;
        public View.OnClickListener onRightSubClickListener;


        public TitleParams(Context context) {
            this.context = context;
        }

        public void apply(TitleBarController controller) {
            controller.setBarColor(barColor);
            controller.setTitleText(title);
            controller.setTitleColor(titleColor);
            controller.setTitleSize(titleSize);

            if (TextUtils.isEmpty(leftText)) {
                controller.createLeftIcon(leftIconSize, leftRegion, leftMargin);
                controller.setLeftIcon(leftIcon);
            } else {
                controller.createLeftText(leftRegion, leftMargin);
                controller.setLeftText(leftText);
                controller.setLeftTextColor(leftTextColor);
                controller.setLeftTextSize(leftTextSize);
            }
            controller.setLeftOnClickListener(onLeftClickListener);

            if (TextUtils.isEmpty(rightText)) {
                controller.createRightIcon(rightIconSize, rightRegion, rightMargin);
                controller.setRightIcon(rightIcon);
            } else {
                controller.createRightText(rightRegion, rightMargin);
                controller.setRightText(rightText);
                controller.setRightTextColor(rightTextColor);
                controller.setRightTextSize(rightTextSize);
            }
            controller.setRightOnClickListener(onRightClickListener);

            if (TextUtils.isEmpty(rightSubText)) {
                controller.createRightSubIcon(rightSubIconSize, rightSubRegion, rightSubMargin);
                controller.setRightSubIcon(rightSubIcon);
            } else {
                controller.createRightSubText(rightSubRegion, rightSubMargin);
                controller.setRightSubText(rightSubText);
                controller.setRightSubTextColor(rightSubTextColor);
                controller.setRightSubTextSize(rightSubTextSize);
            }
            controller.setRightSubOnClickListener(onRightSubClickListener);

        }
    }

}
