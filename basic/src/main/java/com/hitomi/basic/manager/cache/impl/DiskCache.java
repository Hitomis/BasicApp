package com.hitomi.basic.manager.cache.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.hitomi.basic.manager.cache.CacheHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DiskCache implements CacheHandler {
    private static String TAG_CACHE = "=====createTime{createTime}expireMills{expireMills}";
    private static String REGEX = "=====createTime\\{(\\d{1,})\\}expireMills\\{(\\d{1,})\\}";
    private static String DISK_CHACHE_DIR = "file";

    private static final long NO_CACHE = -1L;

    private DiskLruCache cache;
    private Pattern compile;

    private DiskCache(Context context) {
        compile = Pattern.compile(REGEX);
        try {
            File cacheDir = getDiskCacheDir(context, DISK_CHACHE_DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //第一个参数指定的是数据的缓存地址
            //第二个参数指定当前应用程序的版本号[版本号改变，缓存路径下存储的所有数据都会被清除掉]
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1
            // 第四个参数指定最多可以缓存多少字节的数据
            cache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void put(String key, String value, long expireMills) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) return;

        String name = getMd5Key(key);
        try {
            Object existValue = get(name);
            if (existValue != null && existValue instanceof String) {     //如果存在，先删除
                cache.remove(name);
            }

            DiskLruCache.Editor editor = cache.edit(name);
            StringBuilder content = new StringBuilder(value);
            content.append(TAG_CACHE.replace("createTime", "" + Calendar.getInstance().getTimeInMillis()).replace("expireMills", "" + expireMills));
            editor.set(0, content.toString());
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void put(String key, InputStream is) {
        String md5Key = getMd5Key(key);
        DiskLruCache.Snapshot snapShot = null;
        try {
            snapShot = cache.get(md5Key);
            if (snapShot == null) {
                DiskLruCache.Editor edit = cache.edit(md5Key);
                if (doCacheFile(is, edit.newOutputStream(0))) {
                    edit.commit();
                } else {
                    edit.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapShot != null)
                snapShot.close();
        }
    }

    private boolean doCacheFile(InputStream is, OutputStream os) {
        BufferedInputStream in = new BufferedInputStream(is);
        BufferedOutputStream out = new BufferedOutputStream(os);
        try {
            int b;
            final int size = 1024 * 4;
            byte[] buffer = new byte[size];
            while ((b = in.read(buffer, 0, size)) != -1) {
                out.write(buffer, 0, b);
            }
            cache.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
            close(in);
        }
        return false;
    }

    private void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void put(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) return ;
        if (value instanceof InputStream) {
            put(key, (InputStream) value);
        } else {
            put(key, value.toString(), NO_CACHE);
        }
    }

    @Override
    public Object get(String key) {
        Object value = null;
        try {
            String md5Key = getMd5Key(key);
            DiskLruCache.Snapshot snapshot = cache.get(md5Key);
            if (snapshot != null) {
                String content = snapshot.getString(0);
                if (!TextUtils.isEmpty(content)) { // key 映射的是字符
                    Matcher matcher = compile.matcher(content);
                    long createTime = 0;
                    long expireMills = 0;
                    while (matcher.find()) {
                        createTime = Long.parseLong(matcher.group(1));
                        expireMills = Long.parseLong(matcher.group(2));
                    }
                    int index = content.indexOf("=====createTime");

                    if ((createTime + expireMills < Calendar.getInstance().getTimeInMillis())
                            || expireMills == NO_CACHE) {
                        value = content.substring(0, index);
                    } else {
                        //过期
                        cache.remove(md5Key);       //删除
                    }
                } else  { // key 映射的是 InputStream
                    value = snapshot.getInputStream(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void remove(String key) {
        try {
            cache.remove(getMd5Key(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean contains(String key) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(getMd5Key(key));
            return snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void clear() {
        try {
            cache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        compile = null;
        try {
            cache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMd5Key(String key) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(key.getBytes());
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private File getDiskCacheDir(Context context, String dirName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);
    }

    public static DiskCache newInstance(Context context) {
        return new DiskCache(context);
    }
}
