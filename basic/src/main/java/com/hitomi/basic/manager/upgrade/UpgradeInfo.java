package com.hitomi.basic.manager.upgrade;

/**
 * Created by hitomi on 2016/12/14.
 */

public class UpgradeInfo {

    private int versionCode;

    private String apkUrl;

    private String discribe;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }
}
