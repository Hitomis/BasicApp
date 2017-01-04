package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.graphics.Bitmap;
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
class TitleBarController {

    @IdRes
    private static final int ID_RIGHT = 10001;

    private final Context context;
    private final TitleBarLayout titleBar;

    private TextView tvTitle, leftText, rightText, rightSubText;
    private ImageView leftImage, rightImage, rightSubImage;

    TitleBarController(Context context, TitleBarLayout titleBarLayout) {
        this.context = context;
        this.titleBar = titleBarLayout;
    }

    private void createTitle() {
        tvTitle = new TextView(context);
        RelativeLayout.LayoutParams titleRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        titleRlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        titleBar.addView(tvTitle, titleRlp);
    }

    private void setTitle(TextView tvTitle, RelativeLayout.LayoutParams titleRlp) {
        this.tvTitle = tvTitle;
        if (titleRlp == null) {
            titleRlp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            titleRlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        titleBar.addView(tvTitle, titleRlp);
    }

    private void setTitleText(CharSequence title) {
        if (TextUtils.isEmpty(title)) return;
        tvTitle.setText(title);
    }

    private void setTitleColor(int color) {
        if (color == 0) return;
        tvTitle.setTextColor(color);
    }

    private void setTitleSize(float size) {
        if (size <= 0) return;
        tvTitle.setTextSize(size);
    }

    private void setTitleOnClickListener(View.OnClickListener onTitleClickListener) {
        tvTitle.setOnClickListener(onTitleClickListener);
    }

    private void setBarColor(int color) {
        if (color == 0) return;
        titleBar.setBackgroundColor(color);
    }

    private void createLeftText(int leftRegion, int leftMargin) {
        if (leftText != null) return;
        leftText = new TextView(context);
        leftText.setPadding(leftRegion, leftRegion, leftRegion, leftRegion);
        RelativeLayout.LayoutParams leftRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        leftRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        leftRlp.leftMargin = leftMargin;
        titleBar.addView(leftText, leftRlp);
    }

    private void setLeftTextView(TextView tvleft, int leftMargin) {
        leftText = tvleft;
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
        if (leftColor == 0) return;
        leftText.setTextColor(leftColor);
    }

    private void setLeftTextSize(float leftSize) {
        if (leftSize <= 0) return;
        leftText.setTextSize(leftSize);
    }

    private void createLeftImage(int leftImageSize, int leftRegion, int leftMargin) {
        if (leftImage != null) return;
        leftImage = new ImageView(context);
        leftImage.setPadding(leftRegion, leftRegion, leftRegion, leftRegion);
        int imageSize = leftImageSize == 0 ? WRAP_CONTENT : leftImageSize;
        RelativeLayout.LayoutParams leftRlp = new RelativeLayout.LayoutParams(imageSize, imageSize);
        leftRlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        leftRlp.leftMargin = leftMargin;
        titleBar.addView(leftImage, leftRlp);
    }

    private void setLeftIcon(int leftIcon) {
        if (leftIcon == 0) return;
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
        if (rightText != null) return;
        rightText = new TextView(context);
        rightText.setId(ID_RIGHT);
        rightText.setPadding(rightRegion, rightRegion, rightRegion, rightRegion);
        RelativeLayout.LayoutParams rightRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightRlp.rightMargin = rightMargin;
        titleBar.addView(rightText, rightRlp);
    }

    private void setRightTextView(TextView tvRight, int rightMargin) {
        rightText = tvRight;
        rightText.setId(ID_RIGHT);
        RelativeLayout.LayoutParams rightRlp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightRlp.rightMargin = rightMargin;
        titleBar.addView(rightText, rightRlp);
    }

    private void setRightText(CharSequence rightStr) {
        rightText.setText(rightStr);
    }

    private void setRightTextColor(int rightTextColor) {
        if (rightTextColor == 0) return;
        rightText.setTextColor(rightTextColor);
    }

    private void setRightTextSize(float rightTextSize) {
        if (rightTextSize <= 0) return;
        rightText.setTextSize(rightTextSize);
    }

    private void createRightImage(int rightImageSize, int rightRegion, int rightMargin) {
        if (rightImage != null) return;
        rightImage = new ImageView(context);
        rightImage.setId(ID_RIGHT);
        rightImage.setPadding(rightRegion, rightRegion, rightRegion, rightRegion);
        int imageSize = rightImageSize == 0 ? WRAP_CONTENT : rightImageSize;
        RelativeLayout.LayoutParams rightRlp = new RelativeLayout.LayoutParams(imageSize, imageSize);
        rightRlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightRlp.rightMargin = rightMargin;
        titleBar.addView(rightImage, rightRlp);
    }

    private void setRightIcon(int rightIcon) {
        if (rightIcon == 0) return;
        rightImage.setImageResource(rightIcon);
    }

    private void setRightOnClickListener(View.OnClickListener onRightClickListener) {
        if (rightText == null) {
            rightImage.setOnClickListener(onRightClickListener);
        } else {
            rightText.setOnClickListener(onRightClickListener);
        }
    }

    private void createRightSubText(int rightSubRegion, int rightSubMargin) {
        if (rightSubText != null) return;
        rightSubText = new TextView(context);
        rightSubText.setPadding(rightSubRegion, rightSubRegion, rightSubRegion, rightSubRegion);
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
        if (rightSubTextColor == 0) return;
        rightSubText.setTextColor(rightSubTextColor);
    }

    private void setRightSubTextSize(float rightSubTextSize) {
        if (rightSubTextSize <= 0) return;
        rightSubText.setTextSize(rightSubTextSize);
    }

    private void createRightSubImage(int rightSubImageSize, int rightSubRegion, int rightSubMargin) {
        if (rightSubImage != null) return;
        rightSubImage = new ImageView(context);
        rightSubImage.setPadding(rightSubRegion, rightSubRegion, rightSubRegion, rightSubRegion);
        int imageSize = rightSubImageSize == 0 ? WRAP_CONTENT : rightSubImageSize;
        RelativeLayout.LayoutParams rightSubRlp = new RelativeLayout.LayoutParams(imageSize, imageSize);
        rightSubRlp.addRule(RelativeLayout.LEFT_OF, ID_RIGHT);
        rightSubRlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rightSubRlp.rightMargin = rightSubMargin;
        titleBar.addView(rightSubImage, rightSubRlp);
    }

    private void setRightSubIcon(int rightSubIcon) {
        if (rightSubIcon == 0) return;
        rightSubImage.setImageResource(rightSubIcon);
    }

    private void setRightSubOnClickListener(View.OnClickListener onRightSubClickListener) {
        if (rightSubText == null) {
            rightSubImage.setOnClickListener(onRightSubClickListener);
        } else {
            rightSubText.setOnClickListener(onRightSubClickListener);
        }
    }

    private void setRightSubBitmap(Bitmap rightSubBitmap) {
        rightSubImage.setImageBitmap(rightSubBitmap);
    }

    private void setRightBitmap(Bitmap rightBitmap) {
        rightImage.setImageBitmap(rightBitmap);
    }

    private void setLeftBitmap(Bitmap leftBitmap) {
        leftImage.setImageBitmap(leftBitmap);
    }

    static class TitleParams {
        public final Context context;

        int barColor;

        TextView tvTitle;
        RelativeLayout.LayoutParams titleRlp;
        CharSequence title;
        int titleColor;
        float titleSize;

        TextView tvleft;
        CharSequence leftText;
        int leftTextColor;
        float leftTextSize;
        int leftIcon;
        Bitmap leftBitmap;
        int leftImageSize;
        int leftMargin;
        int leftRegion;

        TextView tvRight;
        CharSequence rightText;
        int rightTextColor;
        float rightTextSize;
        int rightIcon;
        Bitmap rightBitmap;
        int rightImageSize;
        int rightMargin;
        int rightRegion;

        CharSequence rightSubText;
        int rightSubTextColor;
        float rightSubTextSize;
        int rightSubIcon;
        Bitmap rightSubBitmap;
        int rightSubImageSize;
        int rightSubMargin;
        int rightSubRegion;

        View.OnClickListener onTitleClickListener;
        View.OnClickListener onLeftClickListener;
        View.OnClickListener onRightClickListener;
        View.OnClickListener onRightSubClickListener;

        TitleParams(Context context) {
            this.context = context;
        }

        void apply(TitleBarController controller) {
            controller.setBarColor(barColor);
            if (tvTitle == null) {
                controller.createTitle();
            } else {
                controller.setTitle(tvTitle, titleRlp);
            }
            controller.setTitleText(title);
            controller.setTitleColor(titleColor);
            controller.setTitleSize(titleSize);
            controller.setTitleOnClickListener(onTitleClickListener);

            if (tvleft != null) {
                controller.setLeftTextView(tvleft, leftMargin);
            } else if (!TextUtils.isEmpty(leftText)) {
                controller.createLeftText(leftRegion, leftMargin);
                controller.setLeftText(leftText);
                controller.setLeftTextColor(leftTextColor);
                controller.setLeftTextSize(leftTextSize);
            } else {
                controller.createLeftImage(leftImageSize, leftRegion, leftMargin);
                if (leftIcon != 0) {
                    controller.setLeftIcon(leftIcon);
                } else if (leftBitmap != null) {
                    controller.setLeftBitmap(leftBitmap);
                }
            }
            controller.setLeftOnClickListener(onLeftClickListener);

            if (tvRight != null) {
                controller.setRightTextView(tvRight, rightMargin);
            } else if (!TextUtils.isEmpty(rightText)) {
                controller.createRightText(rightRegion, rightMargin);
                controller.setRightText(rightText);
                controller.setRightTextColor(rightTextColor);
                controller.setRightTextSize(rightTextSize);
            } else {
                controller.createRightImage(rightImageSize, rightRegion, rightMargin);
                if (rightIcon != 0) {
                    controller.setRightIcon(rightIcon);
                } else if (rightBitmap != null) {
                    controller.setRightBitmap(rightBitmap);
                }
            }
            controller.setRightOnClickListener(onRightClickListener);

            if (TextUtils.isEmpty(rightSubText)) {
                controller.createRightSubImage(rightSubImageSize, rightSubRegion, rightSubMargin);
                if (rightSubIcon != 0) {
                    controller.setRightSubIcon(rightSubIcon);
                } else if (rightSubBitmap != null) {
                    controller.setRightSubBitmap(rightSubBitmap);
                }
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
