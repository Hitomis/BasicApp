package com.hitomi.basicapp.mvp;

import com.google.gson.Gson;
import com.hitomi.basic.net.callback.EntityCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */

public class PersonCallback extends EntityCallback<Person> {
    @Override
    public Person parseNetworkResponse(Response response, int id) throws Exception {
        return new Gson().fromJson(response.body().string(), Person.class);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }
    
}
