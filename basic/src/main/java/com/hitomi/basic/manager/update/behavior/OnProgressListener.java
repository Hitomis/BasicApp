package com.hitomi.basic.manager.update.behavior;

/**
 * Created by hitomi on 2016/12/15.
 */

public interface OnProgressListener {

    void onStart();

    void onProgress(int progress);

    void onFinish();
}
