package com.hitomi.basicapp.mvp;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */

public interface IContract {

    interface View {
        void updateData(String data);
        void showToast(String toast);
    }

    interface Presenter {
        void start();
        void deal();
        void destory();
    }

}
