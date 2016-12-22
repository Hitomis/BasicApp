package com.hitomi.basic.manager.hook;

import android.view.View;

class OnLongClickListenerProxy implements View.OnLongClickListener {
    private View.OnLongClickListener originLongClickListener;
    private HookListenerContract.OnLongClickListener mlistener;

    OnLongClickListenerProxy(View.OnLongClickListener object, HookListenerContract.OnLongClickListener listener) {
        this.originLongClickListener = object;
        this.mlistener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (mlistener != null) mlistener.doInListener(v);
        return originLongClickListener != null && originLongClickListener.onLongClick(v);
    }

    View.OnLongClickListener getOriginLongClickListener() {
        return originLongClickListener;
    }
}
