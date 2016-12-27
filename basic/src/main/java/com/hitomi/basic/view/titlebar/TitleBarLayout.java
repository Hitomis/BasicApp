package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by hitomi on 2016/12/27.
 */

public class TitleBarLayout extends RelativeLayout {

    private TitleConfig config;

    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBaseMode();
    }

    private void initBaseMode() {

    }


}
