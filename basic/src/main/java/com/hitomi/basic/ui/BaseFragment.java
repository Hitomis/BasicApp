package com.hitomi.basic.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

/**
 * Created by hitomi on 2016/12/11.
 */
public abstract class BaseFragment extends Fragment implements UIHandler {

    protected Logger log = XLog.tag(this.getClass().getSimpleName()).build();

    protected View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getContentViewID(), container, false);
        initView();
        setViewListener();
        dealLogic(savedInstanceState);
        return contentView;
    }
}
