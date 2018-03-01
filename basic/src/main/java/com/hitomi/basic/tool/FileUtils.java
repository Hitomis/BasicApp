package com.hitomi.basic.tool;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by Hitomis on 2018/3/1 0001.
 */

public class FileUtils {

    /**
     * 获取安卓手机图片文件夹目录 /storage/emulated/0/Pictures/fileName
     */
    public static File getPicDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), fileName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("Picture directory created");
        }
        return file;
    }

    /**
     * 获取安卓手机音乐文件夹目录 /storage/emulated/0/Music/fileName
     */
    public static File getMusDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC), fileName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("Music directory created");
        }
        return file;
    }

    /**
     * 获取安卓手机视频文件夹目录 /storage/emulated/0/Movie/fileName
     */
    public static File getMovDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), fileName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("Movie directory created");
        }
        return file;
    }

    /**
     * 获取安卓手机下载文件夹目录 /storage/emulated/0/Download/fileName
     */
    public static File getDownloadDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("Download directory created");
        }
        return file;
    }

    /**
     * 获取本 app 缓存文件目录 /storage/emulated/0/Android/data/com.hitomi.basicapp/cache/fileName
     */
    public static File getAppCacheDir(Context context, String fileName) {
        File file = new File(context.getExternalCacheDir(), fileName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("Download directory created");
        }
        return file;
    }

    /**
     * 外部存储是否存在，亦或是否可写入
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 外部存储是否存在，亦或是否可读取
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
