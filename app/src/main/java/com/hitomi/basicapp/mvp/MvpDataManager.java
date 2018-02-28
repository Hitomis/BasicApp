package com.hitomi.basicapp.mvp;

import com.hitomi.basic.net.OkHttpUtils;
import com.hitomi.basicapp.data.PersonCallback;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */
public class MvpDataManager {

    private static class SingletonHolder {
        final static MvpDataManager instance = new MvpDataManager();
    }

    public static MvpDataManager getInstance() {
        return MvpDataManager.SingletonHolder.instance;
    }

    public String getData() {
        return "clicked button";
    }

    public void getDataSync(PersonCallback callback) {
        OkHttpUtils.get()
                .url("https://gw.fdc.com.cn/router/rest")
                .tag(this)
                .addParams("method", "homenhapi.admin.housedetail")
                .addParams("bid", "D61D1BEC-D56C-411F-B1CC-E9F152C58BCD")
                .build()
                .execute(callback);
    }


}
