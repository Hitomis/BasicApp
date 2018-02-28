package com.hitomi.basicapp.mvp;

import com.hitomi.basicapp.data.Person;
import com.hitomi.basicapp.data.PersonCallback;

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
        MvpDataManager.getInstance().getDataSync(new PersonCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                view.showToast(e.toString());
            }

            @Override
            public void onResponse(Person response, int id) {
                view.updateData(response.toString());
            }
        });

    }


    @Override
    public void destory() {
        view = null;
    }
}
