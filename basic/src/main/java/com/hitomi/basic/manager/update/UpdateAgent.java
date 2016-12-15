package com.hitomi.basic.manager.update;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.basic.manager.update.behavior.OnProgressListener;

import java.io.File;
import java.io.InputStream;

public class UpdateAgent {

    private Context mContext;
    private String mUrl;
    private File mTmpFile;
    private File mApkFile;
    private boolean mIsManual = false;
    private boolean mIsWifiOnly = false;

    private UpdateInfo mInfo;
    private UpdateError mError = null;

    private InfoParser mParser;

    private OnFailureListener mOnFailureListener;
    private OnPromptListener mOnPromptListener;

    private OnProgressListener mOnProgressListener;

    public UpdateAgent(Context context, String url, boolean isManual, boolean isWifiOnly) {
        mContext = context;
        mUrl = url;
        mIsManual = isManual;
        mIsWifiOnly = isWifiOnly;
        mOnPromptListener = new OnPrompt(context);
        mOnFailureListener = new OnFailure(context);

        mParser = new DefaultParser();
    }

    public String getUrl() {
        return mUrl;
    }

    public UpdateInfo getInfo() {
        return mInfo;
    }

    public void setInfo(UpdateInfo info) {
        mInfo = info;
    }

    public UpdateError getError() {
        return mError;
    }

    public void setError(UpdateError error) {
        mError = error;
    }

    public void setInfoParser(InfoParser parser) {
        if (parser != null) {
            mParser = parser;
        }
    }

    public void check() {
        if (mIsWifiOnly) {
            if (UpdateUtil.checkWifi(mContext)) {
                onCheck();
            } else {
                onFailure(new UpdateError(UpdateError.CHECK_NO_WIFI));
            }
        } else {
            if (UpdateUtil.checkNetwork(mContext)) {
                onCheck();
            } else {
                onFailure(new UpdateError(UpdateError.CHECK_NO_NETWORK));
            }
        }
    }

    public void parse(InputStream inputStream) {

        try {
            setInfo(mParser.parse(inputStream));
        } catch (Exception e) {
            setError(new UpdateError(UpdateError.CHECK_PARSE));
        }
    }

    public void checkFinish() {
        UpdateError error = getError();
        if (error != null) {
            onFailure(error);
        } else {
            UpdateInfo info = getInfo();
            if (info == null) {
                onFailure(new UpdateError(UpdateError.CHECK_UNKNOWN));
            } else if (!info.isNeedUpdate(mContext)) {
                onFailure(new UpdateError(UpdateError.UPDATE_NO_NEWER));
            } else if (UpdateUtil.isIgnore(mContext, info.getMd5())) {
                onFailure(new UpdateError(UpdateError.UPDATE_IGNORED));
            } else {
                UpdateUtil.setUpdate(mContext, mInfo.getMd5());
                mTmpFile = new File(mContext.getExternalCacheDir(), info.getMd5());
                mApkFile = new File(mContext.getExternalCacheDir(), info.getMd5() + ".apk");
                if (UpdateUtil.verify(mApkFile, mInfo.getMd5())) {
                    onInstall();
                }  else {
                    mOnPromptListener.onPrompt(this);
                }
            }
        }

    }

    public void update() {
        mApkFile = new File(mContext.getExternalCacheDir(), mInfo.getMd5() + ".apk");
        if (UpdateUtil.verify(mApkFile, mInfo.getMd5())) {
            onInstall();
        } else {
            onDownload();
        }
    }

    public void ignore() {
        UpdateUtil.setIgnore(mContext, getInfo().getMd5());
    }

    public void downloadStart() {
        mOnProgressListener.onStart();
    }

    public void downloadProgress(int progress) {
        mOnProgressListener.onProgress(progress);
    }

    public void downloadFinish() {
        mOnProgressListener.onFinish();
        if (mError != null) {
            mOnFailureListener.onFailure(mError);
        } else {
            mTmpFile.renameTo(mApkFile);
            if (mInfo.isAutoInstall()) {
                onInstall();
            }
        }
    }

    public void setProgressListener(OnProgressListener listener) {
        if (listener != null) {
            mOnProgressListener = listener;
        }
    }

    public void setPromptListener(OnPromptListener prompt) {
        if (prompt != null) {
            mOnPromptListener = prompt;
        }
    }

    public void setFailureListener(OnFailureListener failure) {
        if (failure != null) {
            mOnFailureListener = failure;
        }
    }

    private void onFailure(UpdateError error) {
        if (mIsManual || error.isError()) {
            mOnFailureListener.onFailure(error);
        }
    }

    protected void onCheck() {
        new UpdateChecker(this).execute();
    }

    protected void onDownload() {
        new UpdateDownloader(this, mContext, mInfo.getUrl(), mTmpFile).execute();
    }

    protected void onInstall() {
        UpdateUtil.install(mContext, mApkFile, mInfo.isForce());
    }


    public interface OnFailureListener {
        void onFailure(UpdateError error);
    }

    public interface OnPromptListener {
        void onPrompt(UpdateAgent agent);
    }

    public interface InfoParser {
        UpdateInfo parse(InputStream is) throws Exception;
    }

    private class DefaultParser implements InfoParser {
        @Override
        public UpdateInfo parse(InputStream source) throws Exception {
            return new UpdateInfo().parse(source);
        }
    }

    private class OnFailure implements OnFailureListener {

        private Context mContext;

        public OnFailure(Context context) {
            mContext = context;
        }

        @Override
        public void onFailure(UpdateError error) {
            Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class OnPrompt implements UpdateAgent.OnPromptListener {

        private Context mContext;

        public OnPrompt(Context context) {
            mContext = context;
        }

        @Override
        public void onPrompt(UpdateAgent agent) {
            final UpdateInfo info = agent.getInfo();
            String size = Formatter.formatShortFileSize(mContext, info.getSize());
            String content = String.format("最新版本：%1$s\n新版本大小：%2$s\n\n更新内容\n%3$s", info.getVersionName(), size, info.getUpdateContent());

            final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

            dialog.setTitle("应用更新");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);


            float density = mContext.getResources().getDisplayMetrics().density;
            TextView tv = new TextView(mContext);
            tv.setMovementMethod(new ScrollingMovementMethod());
            tv.setVerticalScrollBarEnabled(true);
            tv.setTextSize(14);
            tv.setMaxHeight((int) (250 * density));

            dialog.setView(tv, (int) (25 * density), (int) (15 * density), (int) (25 * density), 0);


            DialogInterface.OnClickListener listener = new OnPromptClick(agent, true);

            if (info.isForce()) {
                tv.setText("您需要更新应用才能继续使用\n\n" + content);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", listener);
            } else {
                tv.setText(content);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "立即更新", listener);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "以后再说", listener);
                if (info.isIgnorable()) {
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略该版", listener);
                }
            }
            dialog.show();
        }
    }

    public class OnPromptClick implements DialogInterface.OnClickListener {
        private final UpdateAgent mAgent;
        private final boolean mIsAutoDismiss;

        public OnPromptClick(UpdateAgent agent, boolean isAutoDismiss) {
            mAgent = agent;
            mIsAutoDismiss = isAutoDismiss;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    mAgent.update();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    mAgent.ignore();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    // not now
                    break;
            }
            if (mIsAutoDismiss) {
                dialog.dismiss();
            }
        }
    }
}