package com.hitomi.basic.view.slideback.callbak;

import android.support.annotation.FloatRange;

import com.hitomi.basic.view.slideback.widget.SlideBackLayout;

public interface OnInternalStateListener {

    void onSlide(@FloatRange(from = 0.0, to = 1.0) float percent);

    void onOpen();

    void onClose(Boolean finishActivity);

    void onCheckPreActivity(SlideBackLayout slideBackLayout);

}
