package com.hitomi.basic.view.slideback.callbak;

import android.support.annotation.FloatRange;

public interface OnSlideListener {

    void onSlide(@FloatRange(from = 0.0, to = 1.0) float percent);

    void onOpen();

    void onClose();

}
