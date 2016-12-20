package com.hitomi.basic.manager.cache;

import android.content.Context;

import com.hitomi.basic.manager.cache.impl.DiskCache;
import com.hitomi.basic.manager.cache.impl.MemoryCache;
import com.hitomi.basic.manager.cache.impl.SharedPref;

/**
 * Created by hitomi on 2016/12/18.
 */
public class CacheManager {

    private static Context app;

    public static void init(Context context) {
        CacheManager.app = context;
    }

    /**
     * 创建一个内存缓存实例 <单例>, 将数据全部缓存在内存中<br/>
     * 可以缓存 app 中任何对象信息, 退出 app 或者手动清空 MemoryCache <br/>
     * (调用{@link CacheHandler#clear()}) {@link MemoryCache} 中保存的数据将全部失效.
     *
     * @return {@link MemoryCache}
     */
    public static CacheHandler MC() {
        return MemoryCache.getInstance();
    }

    /**
     * 创建一个 SharedPreferences 实例 <单例>, 数据将保存在 SharedPreferences 提供的
     * {@link SharedPref#SP_NAME} XML 文件中 <br/>
     * 可以保存 app 中常见类型数据, 手动调用 {@link CacheHandler#clear()} 可以清除数据.
     * @return {@link SharedPref}
     */
    public static SharedPref SP() {
        return SharedPref.getInstance().init(app);
    }

    /**
     * 创建一个磁盘缓存实例 <非单例> <br/>
     * 可以缓存和文件相关的 Drawable，Bitmap，文件输入流 InputStream, File 数据.
     * 其他信息不可以缓存. 手动调用 {@link CacheHandler#clear()} 可以清除缓存.
     * @return {@link DiskCache}
     */
    public static CacheHandler DS() {
        return DiskCache.newInstance(app);
    }

}
