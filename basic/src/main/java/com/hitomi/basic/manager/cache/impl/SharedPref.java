package com.hitomi.basic.manager.cache.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hitomi.basic.manager.cache.CacheHandler;

import java.util.Map;
import java.util.Set;

public class SharedPref implements CacheHandler {

    private static final String SP_NAME = "config";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPref() {}

    private static class SingletonHolder {
        final static SharedPref instance = new SharedPref();
    }

    public static SharedPref getInstance() {
        return SharedPref.SingletonHolder.instance;
    }

    public SharedPref init(Context context){
        if (sharedPreferences == null || editor == null) {
            sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return this;
    }

    @Override
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public void clear() {
        editor.clear().apply();
    }

    @Override
    public void close() {}

    @Override
    public synchronized Object get(String key) {
        Object value = null;
        Map<String, ?> all = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = all.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getKey().equals(key)) {
                value = entry.getValue();
                break;
            }
        }
        return value;
    }

    @Override
    public synchronized void put(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) return ;
        if (value instanceof Integer) {
            putInt(key, Integer.parseInt(value.toString()));
        } else if (value instanceof Float) {
            putFloat(key, Float.parseFloat(value.toString()));
        } else if (value instanceof Long) {
            putLong(key, Long.parseLong(value.toString()));
        } else if (value instanceof Boolean) {
            putBoolean(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof String) {
            putString(key, value.toString());
        }
    }

    private void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    private void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    private void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    private void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

}
