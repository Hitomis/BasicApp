package com.hitomi.basicapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basic.view.titlebar.TitleBarLayout;
import com.hitomi.basicapp.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btnUpdate, btnCache, btnRound, btnRecycle, btnHttp, btnSlide, btnTitlebar, btnPercentLayout;

    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnCache = (Button) findViewById(R.id.btn_cache);
        btnRound = (Button) findViewById(R.id.btn_round);
        btnRecycle = (Button) findViewById(R.id.btn_recycle);
        btnHttp = (Button) findViewById(R.id.btn_http);
        btnSlide = (Button) findViewById(R.id.btn_slide);
        btnTitlebar = (Button) findViewById(R.id.btn_titlebar);
        btnPercentLayout = (Button) findViewById(R.id.btn_percent_layout);
    }

    @Override
    public void setViewListener() {
        btnUpdate.setOnClickListener(this);
        btnCache.setOnClickListener(this);
        btnRound.setOnClickListener(this);
        btnRecycle.setOnClickListener(this);
        btnHttp.setOnClickListener(this);
        btnSlide.setOnClickListener(this);
        btnTitlebar.setOnClickListener(this);
        btnPercentLayout.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        TitleBarLayout titleBar = (TitleBarLayout) findViewById(R.id.title_bar);
        new TitleBarLayout.Builder(this)
                .setTitle("测试标题")
                .setTitleSize(18)
                .setTitleColor(Color.WHITE)
                .setBarColor(Color.parseColor("#74c650"))
                .setup(titleBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                startActivity(new Intent(this, UpdateActivity.class));
                break;
            case R.id.btn_cache:
                startActivity(new Intent(this, CacheActivity.class));
                break;
            case R.id.btn_round:
                startActivity(new Intent(this, ImageActivity.class));
                break;
            case R.id.btn_recycle:
                startActivity(new Intent(this, AdapterActivity.class));
                break;
            case R.id.btn_http:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.btn_slide:
                startActivity(new Intent(this, SlidebackActivity.class));
                break;
            case R.id.btn_titlebar:
                startActivity(new Intent(this, TitlebarActivity.class));
                break;
            case R.id.btn_percent_layout:
                startActivity(new Intent(this, PercentLayoutActivity.class));
                break;
        }
    }
}
