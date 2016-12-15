package com.hitomi.basic.manager.update;

import android.content.Context;

import com.hitomi.basic.manager.update.behavior.OnProgressListener;
import com.hitomi.basic.manager.update.behavior.impl.DialogProgressBehavior;
import com.hitomi.basic.manager.update.behavior.impl.EmptyProgressBehavior;
import com.hitomi.basic.manager.update.behavior.impl.NotificationProgressBehavior;

import java.io.File;

public class UpdateManager {

    public static final int BEHAVIRO_EMPT = 1;
    public static final int BEHAVIRO_NOTIFY = 1 << 1;
    public static final int BEHAVIRO_DIALOG = 1 << 2;

    private UpdateAgent agent;

    private void setAgent(UpdateAgent agent) {
        this.agent = agent;
    }

    public void check() {
        agent.check();
    }

    public void reset() {
        agent.clean();
    }

    public static class Builder {
        private Context context;
        private String url;
        private String channel;
        private boolean isManual; // true：手动检查，所有类型的错误，都会提示，false：只会提醒 2000 以下的错误
        private boolean isWifiOnly;
        private int progressStyle;
        private OnProgressListener progressBehavior;
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

        public Builder setManual(boolean isManual) {
            this.isManual = isManual;
            return this;
        }

        public Builder setWifiOnly(boolean isWifiOnly) {
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        public Builder setProgressStyle(int progressStyle) {
            this.progressStyle = progressStyle;
            return this;
        }

        public Builder setProgressBehavior(OnProgressListener progressBehavior) {
            this.progressBehavior = progressBehavior;
            return this;
        }

        public Builder setParser(UpdateAgent.InfoParser parser) {
            this.parser = parser;
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

        public UpdateManager create() {
            // 创建 apk 文件目录[该路径被 Android 系统认定为应用程序的缓存路径，当程序被卸载的时候，会一起被清除]
            File file = new File(context.getExternalFilesDir("").getParentFile(), "cache");
            if (file != null && !file.exists())
                file.mkdirs();

            // 创建代理
            UpdateAgent agent = new UpdateAgent(context, url, isManual, isWifiOnly);
            // 设置解析器
            agent.setInfoParser(parser);

            // 设定文件下载的进度提醒方式
            if (progressBehavior != null) { // 用户自定义了进度提醒方式，就使用用户自定义的
                agent.setProgressListener(progressBehavior);
            } else if (progressStyle == BEHAVIRO_NOTIFY) {
                agent.setProgressListener(new NotificationProgressBehavior(context, 655));
            } else if (progressStyle == BEHAVIRO_DIALOG) {
                agent.setProgressListener(new DialogProgressBehavior(context));
            } else {
                agent.setProgressListener(new EmptyProgressBehavior());
            }

            agent.setFailureListener(onFailureListener);
            agent.setPromptListener(onPromptListener);

            UpdateManager updateManager = new UpdateManager();
            updateManager.setAgent(agent);
            return updateManager;
        }
    }


}