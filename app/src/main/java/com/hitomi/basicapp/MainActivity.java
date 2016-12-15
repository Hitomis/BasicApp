package com.hitomi.basicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hitomi.basic.manager.update.UpdateManager;
import com.hitomi.basic.ui.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btnJumpNext, btnCheck, btnClean;

    private UpdateManager updateManager;

    String mCheckUrl = "http://img3.fdc.com.cn/app_download/android_upgrade.xml";

    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnJumpNext = (Button) findViewById(R.id.btn_jump_next);
        btnCheck = (Button) findViewById(R.id.btn_check);
        btnClean = (Button) findViewById(R.id.btn_clean);
    }

    @Override
    public void setViewListener() {
        btnJumpNext.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnClean.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {

        updateManager = new UpdateManager.Builder(this)
                .setProgressStyle(UpdateManager.BEHAVIRO_DIALOG)
                .setUrl(mCheckUrl)
                .setManual(true)
                .setWifiOnly(true)
                .setChannel("baidu")
                .create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump_next:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.btn_check:
                updateManager.check();
                break;
            case R.id.btn_clean:
                updateManager.reset();
                break;
        }
    }

}
