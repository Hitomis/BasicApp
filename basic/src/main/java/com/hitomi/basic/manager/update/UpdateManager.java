package com.hitomi.basic.manager.update;

import android.content.Context;

import com.hitomi.basic.manager.update.progress.DialogProgressBehavior;
import com.hitomi.basic.manager.update.progress.EmptyProgressBehavior;
import com.hitomi.basic.manager.update.progress.NotificationProgressBehavior;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * App 版本升级管理
 */
public class UpdateManager {

    /**
     * 空实现，没有任何方式提醒
     */
    public static final int PROGRESS_EMPT = 1;
    /**
     * 提醒栏提醒
     */
    public static final int PROGRESS_NOTIFY = 1 << 1;
    /**
     * 窗口提醒
     */
    public static final int PROGRESS_DIALOG = 1 << 2;

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

    public String readString(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            output.flush();
        } finally {
            close(input);
            close(output);
        }
        return output.toString("UTF-8");
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Builder {
        private Context context;
        private String url; // 检查更新 url
        private String channel; // 渠道
        private boolean isManual; // true：手动检查，所有类型的错误，都会提示，false：只会提醒 2000 以上的错误
        private boolean isWifiOnly; // 是否只在 wifi 环境下检查更新
        private int progressStyle; // 进度提醒方式
        private UpdateAgent.OnProgressListener onProgressListener; // apk 下载进度回调
        private UpdateAgent.OnPromptListener onPromptListener; // 检查返回结果的回调
        private UpdateAgent.OnFailureListener onFailureListener; // 发生错误时的回调
        private UpdateAgent.InfoParser parser;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设定检查更新的接口 url
         * @param url 接口url
         * @return Builder
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设定渠道
         * @param channel 渠道字符串
         * @return Builder
         */
        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         * 是否为手动检查
         * @param isManual true：手动检查，所有类型的错误，都会提示，false：只会提醒 2000 以上的错误
         * @return Builder
         */
        public Builder setManual(boolean isManual) {
            this.isManual = isManual;
            return this;
        }

        /**
         * 是否确定只在 wifi 环境下检查更新
         * @param isWifiOnly true：只在 wifi 环境下检查更新
         * @return Builder
         */
        public Builder setWifiOnly(boolean isWifiOnly) {
            this.isWifiOnly = isWifiOnly;
            return this;
        }

        /**
         * 设定 Apk 下载时进度提醒方式 <br/>
         * {@link #PROGRESS_EMPT} <br/>
         * {@link #PROGRESS_NOTIFY} <br/>
         * {@link #PROGRESS_DIALOG} <br/>
         * @param progressStyle 进度提醒方式
         * @return Builder
         */
        public Builder setProgressStyle(int progressStyle) {
            this.progressStyle = progressStyle;
            return this;
        }

        /**
         * 如果不满足 {@link UpdateManager} 提供的 ProgressStyle，可以设定 OnProgressListener
         * 实现自己的 Apk 下载时进度提醒样式
         * @param listener 进度回调接口
         * @return Builder
         */
        public Builder setOnProgress(UpdateAgent.OnProgressListener listener) {
            this.onProgressListener = listener;
            return this;
        }

        /**
         * 设定一个访问检查更新接口后返回的数据解析器，默认内置解析器
         * {@link UpdateInfo#parse(InputStream)}
         * @param parser 解析器
         * @return Builder
         */
        public Builder setParser(UpdateAgent.InfoParser parser) {
            this.parser = parser;
            return this;
        }

        /**
         * 设定一个访问检查更新接口后更新内容提示回调，默认内置
         * {@link UpdateAgent.OnPrompt}
         * @param listener 更新内容回调接口
         * @return Builder
         */
        public Builder setOnPrompt(UpdateAgent.OnPromptListener listener) {
            onPromptListener = listener;
            return this;
        }

        /**
         * 设定发生错误时的回调，默认内置
         * {@link UpdateAgent.OnFailure}
         * @param listener 错误提示回调接口
         * @return Builder
         */
        public Builder setOnFailure(UpdateAgent.OnFailureListener listener) {
            onFailureListener = listener;
            return this;
        }

        public UpdateManager create() {
            // 创建 apk 文件目录[该路径被 Android 系统认定为应用程序的缓存路径，当程序被卸载的时候，会一起被清除]
            File file = new File(context.getExternalFilesDir("").getParentFile(), "cache");
            if (!file.exists())
                file.mkdirs();

            if ("".equals(channel) || null == channel) {
                url = String.format(url, "");
            } else {
                url = String.format(url, "_" + channel);
            }
            // 创建代理
            UpdateAgent agent = new UpdateAgent(context, url, isManual, isWifiOnly);
            // 设置解析器
            agent.setInfoParser(parser);

            // 设定文件下载的进度提醒方式
            if (onProgressListener != null) { // 用户自定义了进度提醒方式，就使用用户自定义的
                agent.setProgressListener(onProgressListener);
            } else if (progressStyle == PROGRESS_NOTIFY) {
                agent.setProgressListener(new NotificationProgressBehavior(context, 655));
            } else if (progressStyle == PROGRESS_DIALOG) {
                agent.setProgressListener(new DialogProgressBehavior(context));
            } else {
                agent.setProgressListener(new EmptyProgressBehavior());
            }
            // 设置出错时的回调
            agent.setFailureListener(onFailureListener);
            // 设置检查完成后的回调
            agent.setPromptListener(onPromptListener);

            UpdateManager updateManager = new UpdateManager();
            updateManager.setAgent(agent);
            return updateManager;
        }
    }


}