package com.hitomi.basicapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hitomi.basic.manager.update.UpdateAgent;
import com.hitomi.basic.manager.update.UpdateInfo;
import com.hitomi.basic.manager.update.UpdateManager;
import com.hitomi.basic.ui.BaseActivity;
import com.hitomi.basicapp.R;

import java.io.InputStream;

public class UpdateActivity extends BaseActivity implements View.OnClickListener {

    String mCheckUrl = "http://img3.fdc.com.cn/app_download/android_upgrade%s.xml";
    static final String MD5 = "091f341fdda331dc5d57e68607ffd93a";
    private Button btnCheck, btnCheckIgnor, btnCheckForce, btnCheckNotify, btnCheckDialog, btnCheckNone;
    private Button btnJumpNext, btnClean, btnImage;
    private UpdateManager updateManager;

    @Override
    public int getContentViewID() {
        return R.layout.activity_update;
    }

    @Override
    public void initView() {
        btnCheck = (Button) findViewById(R.id.btn_check);
        btnCheckIgnor = (Button) findViewById(R.id.btn_check_ignor);
        btnCheckForce = (Button) findViewById(R.id.btn_check_force);
        btnCheckNotify = (Button) findViewById(R.id.btn_check_notify);
        btnCheckDialog = (Button) findViewById(R.id.btn_check_dialog);
        btnCheckNone = (Button) findViewById(R.id.btn_check_none);

        btnJumpNext = (Button) findViewById(R.id.btn_jump_next);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnImage = (Button) findViewById(R.id.btn_image);
    }

    @Override
    public void setViewListener() {
        btnCheck.setOnClickListener(this);
        btnCheckIgnor.setOnClickListener(this);
        btnCheckIgnor.setOnClickListener(this);
        btnCheckForce.setOnClickListener(this);
        btnCheckNotify.setOnClickListener(this);
        btnCheckDialog.setOnClickListener(this);
        btnCheckNone.setOnClickListener(this);

        btnJumpNext.setOnClickListener(this);
        btnClean.setOnClickListener(this);
        btnImage.setOnClickListener(this);
    }

    @Override
    public void dealLogic(Bundle savedInstanceState) {
        updateManager = new UpdateManager.Builder(this)
                .setProgressStyle(UpdateManager.PROGRESS_DIALOG)
                .setUrl(mCheckUrl)
                .setManual(true)
                .setWifiOnly(true)
                .setParser(new UpdateAgent.InfoParser() {
                    @Override
                    public UpdateInfo parse(InputStream is) throws Exception {
                        return makeUpdateInfoNormal();
                    }
                }).create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_DIALOG)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoNormal();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_check_ignor:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_DIALOG)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoIgnor();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_check_force:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_DIALOG)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoForce();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_check_notify:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_NOTIFY)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoNormal();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_check_dialog:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_DIALOG)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoNormal();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_check_none:
                updateManager = new UpdateManager.Builder(this)
                        .setProgressStyle(UpdateManager.PROGRESS_EMPT)
                        .setUrl(mCheckUrl)
                        .setManual(true)
                        .setWifiOnly(true)
                        .setParser(new UpdateAgent.InfoParser() {
                            @Override
                            public UpdateInfo parse(InputStream is) throws Exception {
                                return makeUpdateInfoNormal();
                            }
                        }).create();
                updateManager.check();
                break;
            case R.id.btn_clean:
                updateManager.reset();
                Toast.makeText(this, "cleared", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jump_next:
                startActivity(new Intent(this, CacheActivity.class));
                break;
            case R.id.btn_image:
                startActivity(new Intent(this, ImageActivity.class));
                break;
        }
    }

    private UpdateInfo makeUpdateInfoNormal() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setAutoInstall(true);
        updateInfo.setForce(false);
        updateInfo.setIgnorable(true);
        updateInfo.setMd5(MD5);
        updateInfo.setSize(23_593_239);
        updateInfo.setUpdateContent("• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；\n" +
                "• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 资料卡新增点赞排行榜，看好友里谁是魅力之王。");
        updateInfo.setUrl("http://img3.fdc.com.cn/app_download/app-yifang-release-1.1.5.apk");
        updateInfo.setVersionCode(10);
        updateInfo.setVersionName("v1.1.5");
        return updateInfo;
    }

    private UpdateInfo makeUpdateInfoIgnor() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setAutoInstall(true);
        updateInfo.setForce(false);
        updateInfo.setIgnorable(false);
        updateInfo.setMd5(MD5);
        updateInfo.setSize(23_593_239);
        updateInfo.setUpdateContent("• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；\n" +
                "• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 资料卡新增点赞排行榜，看好友里谁是魅力之王。");
        updateInfo.setUrl("http://img3.fdc.com.cn/app_download/app-yifang-release-1.1.5.apk");
        updateInfo.setVersionCode(10);
        updateInfo.setVersionName("v1.1.5");
        return updateInfo;
    }

    private UpdateInfo makeUpdateInfoForce() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setAutoInstall(true);
        updateInfo.setForce(true);
        updateInfo.setIgnorable(true);
        updateInfo.setMd5(MD5);
        updateInfo.setSize(23_593_239);
        updateInfo.setUpdateContent("• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；\n" +
                "• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；\n" +
                "• 图片编辑新增艺术滤镜，一键打造文艺画风；\n" +
                "• 资料卡新增点赞排行榜，看好友里谁是魅力之王。");
        updateInfo.setUrl("http://img3.fdc.com.cn/app_download/app-yifang-release-1.1.5.apk");
        updateInfo.setVersionCode(10);
        updateInfo.setVersionName("v1.1.5");
        return updateInfo;
    }
}
