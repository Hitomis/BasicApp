package com.hitomi.basic.manager.update.behavior.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.hitomi.basic.manager.update.behavior.OnProgressListener;

/**
 * Created by hitomi on 2016/12/15.
 */

public class DialogProgressBehavior implements OnProgressListener {

    private Context mContext;
    private ProgressDialog mDialog;

    public DialogProgressBehavior(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMessage("下载中...");
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void onProgress(int i) {
        if (mDialog != null) {
            mDialog.setProgress(i);
        }
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
