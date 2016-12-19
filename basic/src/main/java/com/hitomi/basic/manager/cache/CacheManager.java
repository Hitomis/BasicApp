package com.hitomi.basic.manager.cache;

import android.content.Context;

import com.hitomi.basic.manager.cache.impl.DiskCache;
import com.hitomi.basic.manager.cache.impl.MemoryCache;
import com.hitomi.basic.manager.cache.impl.SharedPref;

/**
 * Created by hitomi on 2016/12/18.
 */
public class CacheManager {

    private static Context mContext;

    private CacheManager() {
    }

    public static void init(Context context) {
        CacheManager.mContext = context;
    }

    public static CacheHandler MC() {
        return MemoryCache.getInstance();
    }

    public static SharedPref SP() {
        return SharedPref.getInstance().init(mContext);
    }

    public static CacheHandler DS() {
        return DiskCache.newInstance(mContext);
    }

}
