package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by hitomi on 2016/12/27.
 */

public class TitleBarController {

    private final Context context;
    private final TitleBarLayout titleBar;

    private TextView tvTitle;

    public TitleBarController(Context context, TitleBarLayout titleBarLayout) {
        this.context = context;
        this.titleBar = titleBarLayout;
    }

    public void prepareContentView() {
        tvTitle = new TextView(context);
        RelativeLayout.LayoutParams titleRlp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        titleRlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        titleBar.addView(tvTitle, titleRlp);
    }

    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    public static class TitleParams {
        public final Context context;

        public String title;
        public int titleColor;
        public int titleSize;
        public int titleIcon;

        public int barColor;
        public int barHeight;

        public String leftText;
        public int leftTextColor;
        public int leftTextSize;
        public int leftIcon;
        public int leftMargin;

        public String rightText;
        public int rightTextColor;
        public int rightTextSize;
        public int rightIcon;
        public int rightMargin;


        public TitleParams(Context context) {
            this.context = context;
        }

        public void apply(TitleBarController controller) {
            if (!TextUtils.isEmpty(title)) {
                controller.setTitle(title);
            }
        }
    }


}
