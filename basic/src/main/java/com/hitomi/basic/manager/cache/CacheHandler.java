package com.hitomi.basic.manager.cache;

/**
 * Created by hitomi on 2016/12/18.
 */
public interface CacheHandler {

    /**
     * 存放数据，表现形式为键值对
     * @param key 键
     * @param value 值
     */
    void put(String key, Object value);

    /**
     * 查询并返回 与 key 相映射的数据信息
     * @param key 数据信息对应的键
     * @return key 对应的数据
     */
    Object get(String key);

    /**
     * 移除与 key 相映射的数据信息
     * @param key 数据信息对应的键
     */
    void remove(String key);

    /**
     * 是否包含与 key 相映射的数据信息
     * @param key 数据信息对应的键
     * @return true：包含
     */
    boolean contains(String key);

    /**
     * 清除缓存
     */
    void clear();

    /**
     * 关闭示例，当前缓存不再可用
     */
    void close();

}
