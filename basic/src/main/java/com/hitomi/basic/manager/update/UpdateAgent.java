package com.hitomi.basic.manager.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.basic.manager.update.behavior.OnProgressListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

class UpdateAgent {

    private static final String PREFS = "hitomi.update.prefs";
    private static final String PREFS_IGNORE = "hitomi.update.prefs.ignore";
    private static final String PREFS_UPDATE = "hitomi.update.prefs.update";

    private Context mContext;
    private String mUrl;
    private File mTmpFile;
    private File mApkFile;
    private boolean mIsManual = false;
    private boolean mIsWifiOnly = false;

    private UpdateInfo mInfo;
    private UpdateError mError;

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
            if (checkWifi()) {
                onCheck();
            } else {
                onFailure(new UpdateError(UpdateError.CHECK_NO_WIFI));
            }
        } else {
            if (checkNetwork()) {
                onCheck();
            } else {
                onFailure(new UpdateError(UpdateError.CHECK_NO_NETWORK));
            }
        }
    }

    public void clean() {
        SharedPreferences sp = mContext.getSharedPreferences(PREFS, 0);
        File file = new File(mContext.getExternalCacheDir(), sp.getString(PREFS_UPDATE, "") + ".apk");
        if (file.exists()) file.delete();
        File tempFile = new File(mContext.getExternalCacheDir(), sp.getString(PREFS_UPDATE, ""));
        if (tempFile.exists()) tempFile.delete();
        sp.edit().clear().apply();
    }

    public void parse(InputStream inputStream) {
        try {
            mInfo = mParser.parse(inputStream);
        } catch (Exception e) {
            setError(new UpdateError(UpdateError.CHECK_PARSE));
        }
    }

    public boolean checkNetwork() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private boolean checkWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean verify(File apk, String md5) {
        if (!apk.exists()) {
            return false;
        }
        String _md5 = md5(apk);
        if (TextUtils.isEmpty(_md5)) {
            return false;
        }
        boolean result = _md5 != null && _md5.equalsIgnoreCase(md5);
        if (!result) {
            apk.delete();
        }
        return result;
    }

    public String md5(File file) {
        MessageDigest digest;
        FileInputStream fis;
        byte[] buffer = new byte[1024];

        try {
            if (!file.isFile()) {
                return "";
            }

            digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);

            while (true) {
                int len;
                if ((len = fis.read(buffer, 0, 1024)) == -1) {
                    fis.close();
                    break;
                }

                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        BigInteger var5 = new BigInteger(1, digest.digest());
        return String.format("%1$032x", new Object[]{var5});
    }

    private void prepareUpdate() {
        String md5 = mInfo.getMd5();
        if (TextUtils.isEmpty(md5)) {
            return;
        }
        SharedPreferences sp = mContext.getSharedPreferences(PREFS, 0);
        String old = sp.getString(PREFS_UPDATE, "");
        if (md5.equals(old)) {
            return;
        }
        File oldFile = new File(mContext.getExternalCacheDir(), old);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        sp.edit().putString(PREFS_UPDATE, md5).apply();
        File file = new File(mContext.getExternalCacheDir(), md5);
        if (!file.exists()) {
            try {
                // 创建 apk 临时文件
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkFinish() {
        if (mError != null) {
            onFailure(mError);
        } else {
            if (mInfo == null) {
                onFailure(new UpdateError(UpdateError.CHECK_UNKNOWN));
            } else if (!mInfo.isNeedUpdate(mContext)) { // 最新版本
                onFailure(new UpdateError(UpdateError.UPDATE_NO_NEWER));
            } else if (isIgnore()) { // 该版本已经忽略
                onFailure(new UpdateError(UpdateError.UPDATE_IGNORED));
            } else {
                prepareUpdate();
                mTmpFile = new File(mContext.getExternalCacheDir(), mInfo.getMd5());
                mApkFile = new File(mContext.getExternalCacheDir(), mInfo.getMd5() + ".apk");
                if (verify(mApkFile, mInfo.getMd5())) {
                    onInstall();
                } else {
                    mOnPromptListener.onPrompt(this);
                }
            }
        }
    }

    public void update() {
        mApkFile = new File(mContext.getExternalCacheDir(), mInfo.getMd5() + ".apk");
        if (verify(mApkFile, mInfo.getMd5())) {
            onInstall();
        } else {
            onDownload();
        }
    }

    public void ignore() {
        mContext.getSharedPreferences(PREFS, 0).edit().putString(PREFS_IGNORE, mInfo.getMd5()).apply();
    }

    private boolean isIgnore() {
        return !TextUtils.isEmpty(mInfo.getMd5()) &&
                mInfo.getMd5().equals(mContext.getSharedPreferences(PREFS, 0).getString(PREFS_IGNORE, ""));
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

    private void onCheck() {
        new UpdateChecker(this).execute();
    }

    private void onDownload() {
        new UpdateDownloader(this, mContext, mInfo.getUrl(), mTmpFile).execute();
    }

    private void onInstall() {
        String md5 = mContext.getSharedPreferences(PREFS, 0).getString(PREFS_UPDATE, "");
        File apk = new File(mContext.getExternalCacheDir(), md5 + ".apk");
        if (verify(apk, md5)) {
            onInstall(apk);
        }
    }

    private void onInstall(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".updatefileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        if (mInfo.isForce()) {
            System.exit(0);
        }
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
            final UpdateInfo info = mInfo;
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

    private class OnPromptClick implements DialogInterface.OnClickListener {
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