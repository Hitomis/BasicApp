package com.hitomi.basic.manager.upgrade;

import android.content.Context;

/**
 * Created by hitomi on 2016/12/14.
 */

public class UpgradeManager {

    private Context context;

    private String upgradeUrl;
    private String channel;
    private String apkPath;
    private String apkName;
    private boolean isWifiUpgrade;

    private UpgradTask upgradTask;

    private static class SingletonHolder {
        public final static UpgradeManager instance = new UpgradeManager();
    }

    public static UpgradeManager getInstance() {
        return UpgradeManager.SingletonHolder.instance;
    }

    public UpgradeManager init(Context context) {
        this.context = context;

        return this;
    }

    public UpgradeManager setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
        return this;
    }

    public UpgradeManager setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public UpgradeManager setWifiUpgrade(boolean wifiUpgrade) {
        isWifiUpgrade = wifiUpgrade;
        return this;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public void checkUpgrade() {

    }
}
