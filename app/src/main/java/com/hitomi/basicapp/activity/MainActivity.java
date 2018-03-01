package com.hitomi.basicapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.hitomi.basic.tool.FileUtils;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basic.view.titlebar.TitleBarLayout;
import com.hitomi.basicapp.R;
import com.orhanobut.logger.Logger;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_PERMISSION = 233;
    private Button btnUpdate, btnCache, btnRound, btnRecycle, btnHttp, btnTitlebar, btnPercentLayout, btnMvp;

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
        btnTitlebar = (Button) findViewById(R.id.btn_titlebar);
        btnPercentLayout = (Button) findViewById(R.id.btn_percent_layout);
        btnMvp = (Button) findViewById(R.id.btn_mvp_demo);
    }

    @Override
    public void setViewListener() {
        btnUpdate.setOnClickListener(this);
        btnCache.setOnClickListener(this);
        btnRound.setOnClickListener(this);
        btnRecycle.setOnClickListener(this);
        btnHttp.setOnClickListener(this);
        btnTitlebar.setOnClickListener(this);
        btnPercentLayout.setOnClickListener(this);
        btnMvp.setOnClickListener(this);
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
            case R.id.btn_titlebar:
                startActivity(new Intent(this, TitlebarActivity.class));
                break;
            case R.id.btn_percent_layout:
                startActivity(new Intent(this, PercentLayoutActivity.class));
                break;
            case R.id.btn_mvp_demo:
//                startActivity(new Intent(this, MvpActivity.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(this, STORAGE_PERMISSIONS[0]) != PERMISSION_GRANTED) {
                    requestPermissions(STORAGE_PERMISSIONS, REQUEST_CODE_PERMISSION);
                } else {
                    Logger.d(FileUtils.getAppCacheDir(this, "hitomis"));
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_CODE_PERMISSION == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.d(FileUtils.getAppCacheDir(this, "hitomis"));
            } else {
                Logger.d("没有权限");
            }
        }
    }
}
