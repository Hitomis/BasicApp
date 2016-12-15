package com.hitomi.basicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hitomi.basic.manager.update.UpdateManager;
import com.hitomi.basic.ui.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btnJumpNext;

    String mCheckUrl = "http://img3.fdc.com.cn/app_download/android_upgrade.xml";

    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnJumpNext = (Button) findViewById(R.id.btn_jump_next);
    }

    @Override
    public void setViewListener() {
        btnJumpNext.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {

        new UpdateManager.Builder(this)
                .setUrl(mCheckUrl)
                .setManual(false)
                .setWifiOnly(true)
                .setChannel("baidu")
                .check();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump_next:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
    }

}
