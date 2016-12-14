package com.hitomi.basic.manager.upgrade;

import android.content.Context;
import android.text.TextUtils;

import com.hitomi.basic.manager.NetworkManager;

/**
 * Created by hitomi on 2016/12/14.
 */

public class UpgradeManager {

    private Context context;

    private String upgradeUrl; // 升级检查地址
    private String channel = "companyName"; // 渠道
    private String apkPath = "upgrade"; // 下载后 apk 文件存放的文件夹名称
    private String apkName = "app.apk"; // 下载后 apk 名称
    private boolean isWifiUpgrade = true; // 是否只在 wifi 网络环境下更新

    private UpgradTask upgradTask; // 后台检查更新任务[包含版本检查和 apk 下载]
    private UpgradeParser upgradeParser;

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

    public UpgradeManager setApkName(String apkName) {
        this.apkName = apkName;
        return this;
    }

    public UpgradeManager setApkPath(String apkPath) {
        this.apkPath = apkPath;
        return this;
    }

    public UpgradeManager loadParser(UpgradeParser parser) {
        this.upgradeParser = parser;
        return this;
    }

    public void checkUpgrade() {
        if (!verifyCondition()) return ;
        if (!(isWifiUpgrade && NetworkManager.getInstance().isWifiNet())) return ;

        upgradTask = new UpgradTask(upgradeUrl, upgradeParser);
        upgradTask.check();
    }

    private boolean verifyCondition() {
        if (TextUtils.isEmpty(upgradeUrl) || context == null)
            return false;
        return true;
    }
}
