package com.hitomi.basic.manager.upgrade;

import android.os.Handler;
import android.os.Message;
import android.util.Xml;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

/**
 * Created by hitomi on 2016/12/14.
 */

public class UpgradTask {

    private static final int CHECK_SUCCESS = 1;
    private static final int CHECK_FAILURE = -1;

    private Logger log = XLog.tag("UpgradeManager").build();

    private String checkUrl;

    public UpgradTask(String url, UpgradeParser parser) {
        checkUrl = url;
        if (parser != null)
            upgradeParser = parser;
    }

    /**
     * 内置一个默认解析 xml 文件流的解析器
     */
    private UpgradeParser upgradeParser = new UpgradeParser() {
        @Override
        public UpgradeInfo doParse(InputStream is) {
            UpgradeInfo info = null;
            try {
                info = parseAction(is);
            } catch (Exception e) {
                log.e(e);
            }
            return info;
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHECK_SUCCESS) {

            } else {
                log.e("app 版本更新检查失败");
            }
        }
    };

    private UpgradeInfo parseAction(InputStream is) throws Exception {
        UpgradeInfo info = null;
        XmlPullParser parser = Xml.newPullParser();
        int type = parser.getEventType();
        parser.setInput(is, "utf-8");
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("appInfo".equals(parser.getName())) {
                        info = new UpgradeInfo();
                    } else if ("version".equals(parser.getName())) {
                        info.setVersionCode(Integer.parseInt(parser.nextText()));
                    } else if ("downloadUrl".equals(parser.getName())) {
                        info.setApkUrl(parser.nextText());
                    } else if ("desc".equals(parser.getName())) {
                        info.setDiscribe(parser.nextText());
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

    public void check() {
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                InputStream is = null;
                Message msg = handler.obtainMessage();
                try {
                    URL url = new URL(checkUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        is = conn.getInputStream();
                        UpgradeInfo info = upgradeParser.doParse(is);
                        msg.what = CHECK_SUCCESS;
                        msg.obj = info;
                    } else {
                        msg.what = CHECK_FAILURE;
                    }
                } catch (Exception e) {
                    msg.what = CHECK_FAILURE;
                    log.e(e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            log.e(e);
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                    msg.sendToTarget();
                }
            }
        });
    }

    public void download() {

    }

}
