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

public class UpdateManager {

    public static final int PROGRESS_EMPT = 1;
    public static final int PROGRESS_NOTIFY = 1 << 1;
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
            int n = 0;
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

    public void close(Closeable closeable) {
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
        private String url;
        private String channel;
        private boolean isManual; // true：手动检查，所有类型的错误，都会提示，false：只会提醒 2000 以上的错误
        private boolean isWifiOnly;
        private int progressStyle;
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

        public Builder setOnProgress(UpdateAgent.OnProgressListener listener) {
            this.onProgressListener = listener;
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