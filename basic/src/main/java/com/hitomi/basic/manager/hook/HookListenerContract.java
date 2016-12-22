package com.hitomi.basic.manager.hook;

import android.view.View;

public class HookListenerContract {

    public interface OnFocusChangeListener {
        void doInListener(View v, boolean hasFocus);
    }


    public interface OnClickListener {
        boolean doInListener(View v);
    }


    public interface OnLongClickListener {
        void doInListener(View v);
    }

}
