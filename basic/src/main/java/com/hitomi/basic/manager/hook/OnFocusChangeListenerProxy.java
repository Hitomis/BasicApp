package com.hitomi.basic.manager.hook;

import android.view.View;

class OnFocusChangeListenerProxy implements View.OnFocusChangeListener {
    private View.OnFocusChangeListener originFocusChangeListener;
    private HookListenerContract.OnFocusChangeListener mlistener;

    OnFocusChangeListenerProxy(View.OnFocusChangeListener object, HookListenerContract.OnFocusChangeListener listener) {
        this.originFocusChangeListener = object;
        this.mlistener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mlistener != null) mlistener.doInListener(v, hasFocus);
        if (originFocusChangeListener != null) originFocusChangeListener.onFocusChange(v, hasFocus);
    }

    View.OnFocusChangeListener getOriginFocusChangeListener() {
        return originFocusChangeListener;
    }
}
