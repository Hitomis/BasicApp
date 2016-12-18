package com.hitomi.basic.manager.cache;

/**
 * Created by hitomi on 2016/12/18.
 */
public interface CacheHandler {

    void put(String key, Object value);

    Object get(String key);

    void remove(String key);

    boolean contains(String key);

    void clear();

}
