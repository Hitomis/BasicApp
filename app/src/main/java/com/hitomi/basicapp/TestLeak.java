package com.hitomi.basicapp;

import android.widget.TextView;

/**
 * Created by hitomi on 2016/12/13.
 */

public class TestLeak {

    private static TestLeak sInstance;
    private TextView mRetainedTextView;

    public static TestLeak getInstance() {
        if (sInstance == null) {
            sInstance = new TestLeak();
        }
        return sInstance;
    }

    public void setRetainedTextView(TextView textView) {
        mRetainedTextView = textView;
    }
}
