package com.hitomi.basic.ui;

import android.os.Bundle;

/**
 * Created by hitomi on 2016/12/11.
 */
public interface UIHandler {

    int getContentViewID();

    void initView();

    void setViewListener();

    void dealLogic(Bundle savedInstanceState);
}
