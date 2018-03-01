package com.hitomi.basic.tool.helper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hitomis on 2018/3/1 0001.
 */
public class ThreadHelper {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final ThreadPoolExecutor executorService;

    private ThreadHelper() {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue);
    }

    private static class SingletonHolder {
        private final static ThreadHelper instance = new ThreadHelper();
    }

    public static ThreadHelper getInstance() {
        return ThreadHelper.SingletonHolder.instance;
    }

    public void exec(Runnable runnable) {
        executorService.execute(runnable);
    }

}
