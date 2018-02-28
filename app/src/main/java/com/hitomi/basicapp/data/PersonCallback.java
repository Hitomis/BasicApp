package com.hitomi.basicapp.data;

import com.google.gson.Gson;
import com.hitomi.basic.net.callback.Callback;

import okhttp3.Response;

/**
 * Created by Hitomis on 2018/2/28 0028.
 */

public abstract class PersonCallback extends Callback<Person> {

    @Override
    public Person parseNetworkResponse(Response response, int id) throws Exception {
        return new Gson().fromJson(response.body().toString(), Person.class);
    }
}
