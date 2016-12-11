package com.hitomi.basic.net.builder;

import com.hitomi.basic.net.OkHttpUtils;
import com.hitomi.basic.net.request.OtherRequest;
import com.hitomi.basic.net.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
