package com.hitomi.basic.manager.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UpdateInfo {
    // 是否静默下载：有新版本时不提示直接下载
    private boolean isSilent = false;
    // 是否强制安装：不安装无法使用app
    private boolean isForce = false;
    // 是否下载完成后自动安装
    private boolean isAutoInstall = true;
    // 是否可忽略该版本
    private boolean isIgnorable = true;
    private int versionCode;
    private String versionName;
    private String updateContent;
    private String url;
    private String md5;
    private long size;

    public UpdateInfo parse(InputStream is) throws Exception {
        UpdateInfo info = null;
        XmlPullParser parser = Xml.newPullParser();
        int type = parser.getEventType();
        parser.setInput(is, "utf-8");
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("appInfo".equals(parser.getName())) {
                        info = new UpdateInfo();
                    } else if ("version".equals(parser.getName())) {
                        // TODO : 这里为了测试先写一个确定数字
                        info.versionCode = 2;
                    } else if ("downloadUrl".equals(parser.getName())) {
                        info.url = parser.nextText();
                        // TODO : 这里为了测试先固定住MD5
                        info.md5 = "97b311cf068f3b24e7fc755a7cd3277d";
                    } else if ("desc".equals(parser.getName())) {
                        info.updateContent = parser.nextText();
                    } else if ("size".equals(parser.getName())) {
                        info.size = Long.parseLong(parser.nextText());
                    } else if ("versionName".equals(parser.getName())) {
                        info.versionName = parser.nextText();
                    } else if ("md5".equals(parser.getName())) {
                        info.md5 = parser.nextText();
                    } else if ("isSilent".equals(parser.getName())) {
                        info.isSilent = Boolean.parseBoolean(parser.nextText());
                    } else if ("isForce".equals(parser.getName())) {
                        info.isForce = Boolean.parseBoolean(parser.nextText());
                    } else if ("isAutoInstall".equals(parser.getName())) {
                        info.isAutoInstall = Boolean.parseBoolean(parser.nextText());
                    } else if ("isIgnorable".equals(parser.getName())) {
                        info.isIgnorable = Boolean.parseBoolean(parser.nextText());
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

    public boolean isNeedUpdate(Context context) {
        boolean isNeedUpdate = false;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (versionCode > info.versionCode)
                isNeedUpdate = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return isNeedUpdate;
    }

    public String md5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isAutoInstall() {
        return isAutoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public void setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}