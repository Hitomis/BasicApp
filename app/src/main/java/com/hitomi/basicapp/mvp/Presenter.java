package com.hitomi.basicapp.mvp;

import com.hitomi.basic.net.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */

public class Presenter implements IContract.Presenter {
    private IContract.View view;

    public Presenter(IContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        String s = MvpDataManager.getInstance().getData();
        view.showToast(s);
    }

    @Override
    public void deal() {
        MvpDataManager.getInstance().getDataSync(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                view.showToast(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                view.updateData(response);
            }
        });

    }


    @Override
    public void destory() {
        view = null;
    }
}
