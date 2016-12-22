package com.hitomi.basic.manager.hook;

import android.view.View;

class OnClickListenerProxy implements View.OnClickListener {
    private View.OnClickListener originClickListener;
    private HookListenerContract.OnClickListener mlistener;

    OnClickListenerProxy(View.OnClickListener object, HookListenerContract.OnClickListener listener) {
        this.originClickListener = object;
        this.mlistener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mlistener != null && !mlistener.doInListener(v))
        if (originClickListener != null) originClickListener.onClick(v);
    }

    View.OnClickListener getOriginOnClickListener() {
        return originClickListener;
    }
}
