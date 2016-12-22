package com.hitomi.basic.manager.hook;

import android.view.View;

public class HookListenerContract {

    public interface OnFocusChangeListener {
        void doInListener(View v, boolean hasFocus);
    }


    public interface OnClickListener {
        /**
         * 执行钩子函数的点击事件
         * @param v 点击事件的宿主 View
         * @return true: 拦截当前点击事件, false: 将点击事件传递下去
         */
        boolean doInListener(View v);
    }


    public interface OnLongClickListener {
        void doInListener(View v);
    }

}
