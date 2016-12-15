package com.hitomi.basic.manager.update.behavior;

/**
 * Created by hitomi on 2016/12/15.
 */

public interface OnProgressListener {

    int BEHAVIRO_EMPT = 1;
    int BEHAVIRO_NOTIFY = 1 << 1;
    int BEHAVIRO_DIALOG = 1 << 2;

    void onStart();

    void onProgress(int progress);

    void onFinish();
}
