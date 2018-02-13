package com.hitomi.basicapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hitomi.basic.net.OkHttpUtils;
import com.hitomi.basic.net.callback.StringCallback;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basicapp.R;
import com.orhanobut.logger.Logger;

import okhttp3.Call;

public class OkHttpActivity extends BaseActivity implements View.OnClickListener {


    private Button btnGet, btnPost;


    @Override
    public int getContentViewID() {
        return R.layout.activity_ok_http;
    }

    @Override
    public void initView() {
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
    }

    @Override
    public void setViewListener() {
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                doGet();
                break;
            case R.id.btn_post:
                doPost();
                break;
        }
    }

    private void doPost() {
        OkHttpUtils.post()
                .url("https://gw.fdc.com.cn/router/rest")
                .tag(this)
                .addParams("method", "homenhapi.admin.housedetail")
                .addParams("bid", "D61D1BEC-D56C-411F-B1CC-E9F152C58BCD")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(OkHttpActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(OkHttpActivity.this, "请求成功，请查看控制台日志", Toast.LENGTH_SHORT).show();
                        Logger.json(response);
                    }

                });
    }

    private void doGet() {
        OkHttpUtils.get()
                .url("https://gw.fdc.com.cn/router/rest")
                .tag(this)
                .addParams("method", "homenhapi.admin.housedetail")
                .addParams("bid", "D61D1BEC-D56C-411F-B1CC-E9F152C58BCD")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(OkHttpActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(OkHttpActivity.this, "请求成功，请查看控制台日志", Toast.LENGTH_SHORT).show();
                        Logger.json(response);
                    }

                });
    }
}
