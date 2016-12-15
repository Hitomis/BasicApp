package com.hitomi.basic.manager.update;

import android.content.Context;

import java.io.File;

public class UpdateManager {

    private UpdateAgent agent;

    private void setAgent(UpdateAgent agent) {
        this.agent = agent;
    }

    public void check() {
        agent.check();
    }

    public static class Builder {
        private Context context;
        private String url;
        private String channel;
        private boolean isManual;
        private boolean isWifiOnly;
        private int notifyId = 0;
        private UpdateAgent.OnProgressListener onNotifyListener;
        private UpdateAgent.OnProgressListener onProgressListener;
        private UpdateAgent.OnPromptListener onPromptListener;
        private UpdateAgent.OnFailureListener onFailureListener;
        private UpdateAgent.InfoParser parser;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setNotifyId(int notifyId) {
            this.notifyId = notifyId;
            return this;
        }

        public Builder setManual(boolean isManual) {
            this.isManual = isManual;
            return this;
        }

        public Builder setWifiOnly(boolean isWifiOnly) {
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        public Builder setParser(UpdateAgent.InfoParser parser) {
            this.parser = parser;
            return this;
        }

        public Builder setOnNotify(UpdateAgent.OnProgressListener listener) {
            onNotifyListener = listener;
            return this;
        }

        public Builder setOnProgress(UpdateAgent.OnProgressListener listener) {
            onProgressListener = listener;
            return this;
        }

        public Builder setOnPrompt(UpdateAgent.OnPromptListener listener) {
            onPromptListener = listener;
            return this;
        }

        public Builder setOnFailure(UpdateAgent.OnFailureListener listener) {
            onFailureListener = listener;
            return this;
        }

        private UpdateManager create() {
            // 创建 apk 文件目录[该路径被 Android 系统认定为应用程序的缓存路径，当程序被卸载的时候，会一起被清除]
            File file = new File(context.getExternalFilesDir("").getParentFile(), "cache");
            if (file != null && !file.exists()) {
                file.mkdirs();
            }

            // 创建代理
            UpdateAgent agent = new UpdateAgent(context, url, isManual, isWifiOnly);
            agent.setInfoParser(parser);
            if (onNotifyListener != null) {
                agent.setNotifyListener(onNotifyListener);
            } else if (notifyId > 0) {
                agent.setNotifyListener(new UpdateAgent.NotificationProgress(context, notifyId));
            }
            agent.setFailureListener(onFailureListener);
            agent.setPromptListener(onPromptListener);
            agent.setProgressListener(onProgressListener);

            UpdateManager updateManager = new UpdateManager();
            updateManager.setAgent(agent);
            return updateManager;
        }

        public void check() {
            UpdateManager updateManager = create();
            updateManager.check();
        }
    }



}