package com.hitomi.basicapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basicapp.R;

public class SlidebackActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNext;
    private Button btnExitApp;

    @Override
    public int getContentViewID() {
        return R.layout.activity_slideback;
    }

    @Override
    public void initView() {
        btnNext = (Button) findViewById(R.id.btn_next);
        btnExitApp = (Button) findViewById(R.id.btn_exit_app);
    }

    @Override
    public void setViewListener() {
        btnNext.setOnClickListener(this);
        btnExitApp.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            startActivity(new Intent(this, SlidebackActivity.class));
        } else if (v.getId() == R.id.btn_exit_app) {
            postExitApp();
        }
    }
}
