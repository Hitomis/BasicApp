package com.hitomi.basicapp;

import android.os.Bundle;
import android.widget.TextView;

import com.hitomi.basic.ui.BaseActivity;

public class HomeActivity extends BaseActivity {



    @Override
    public int getContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewListener() {

    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        TextView textView = (TextView) findViewById(R.id.tv);
        TestLeak.getInstance().setRetainedTextView(textView);
    }
}
