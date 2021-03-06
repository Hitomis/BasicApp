package com.hitomi.basic.manager.cache.impl;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.hitomi.basic.manager.cache.CacheHandler;


public class MemoryCache implements CacheHandler {

    private LruCache<String, Object> cache;

    private MemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        cache = new LruCache<>(cacheSize);
    }

    private static class SingletonHolder {
        final static MemoryCache instance = new MemoryCache();
    }

    public static MemoryCache getInstance() {
        return MemoryCache.SingletonHolder.instance;
    }

    @Override
    public synchronized void put(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) return;

        if (cache.get(key) != null) {
            cache.remove(key);
        }
        cache.put(key, value);
    }

    @Override
    public synchronized Object get(String key) {
        return cache.get(key);
    }

    public synchronized <T> T get(String key, Class<T> clazz) {
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String key) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
    }

    @Override
    public boolean contains(String key) {
        return cache.get(key) != null;
    }

    @Override
    public void clear() {
        cache.evictAll();
    }

    @Override
    public void close() {
        clear();
        cache = null;
    }


}
