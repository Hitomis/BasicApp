package com.hitomi.basic.manager.hook;

public class ListenerManager {
    HookListenerContract.OnFocusChangeListener mOnFocusChangeListener;
    HookListenerContract.OnClickListener mOnClickListener;
    HookListenerContract.OnLongClickListener mOnLongClickListener;

    private ListenerManager() {
    }

    public static ListenerManager create(Builer builer) {
        if (builer == null) {
            return null;
        }
        return builer.build();
    }

    public static class Builer {
        private ListenerManager listenerManager = new ListenerManager();

        public Builer buildOnFocusChangeListener(HookListenerContract.OnFocusChangeListener onFocusChangeListener) {
            listenerManager.mOnFocusChangeListener = onFocusChangeListener;
            return this;
        }

        public Builer buildOnClickListener(HookListenerContract.OnClickListener onClickListener) {
            listenerManager.mOnClickListener = onClickListener;
            return this;
        }

        public Builer buildOnLongClickListener(HookListenerContract.OnLongClickListener onLongClickListener) {
            listenerManager.mOnLongClickListener = onLongClickListener;
            return this;
        }

        public ListenerManager build() {
            return listenerManager;
        }
    }
}
