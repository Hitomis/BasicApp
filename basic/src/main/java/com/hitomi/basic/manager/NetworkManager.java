package com.hitomi.basic.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * APP 网络管理
 *
 * Created by hitomi on 2016/12/12.
 */
public class NetworkManager {

    private static final int STATUS_NETWORK_UNAVAILABLE = -100;
    private static final int STATUS_WIFI_AVAILABLE = 100;
    private static final int STATUS_MOBILENET_AVAILABLE = 101;

    private Logger log = XLog.tag("NetworkManager").build();

    private Context mContext;
    private ConnectivityManager connManager;
    private OnNetworkStatusChangeListener networkListener;
    private NetWorkChangeReceiver netWorkChangeReceiver;

    private int lastConnType;

    private class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int connType;
            // 监听wifi的打开与关闭，与wifi的连接无关
            // [用来确定 wifi 关闭]
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                if (wifiState == WifiManager.WIFI_STATE_DISABLED) { // 关闭wifi
                    connType = STATUS_NETWORK_UNAVAILABLE;
                    callback(connType);
                }
            }

            // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
            // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
            // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
            // 当然刚打开wifi肯定还没有连接到有效的无线
            // [用来确定 wifi 可用]
            else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    if (state == NetworkInfo.State.CONNECTED) {// 更精确的确定状态
                        connType = STATUS_WIFI_AVAILABLE;
                        callback(connType);
                    }
                }
            }

            // [用来确定移动网络可用]
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    if (activeNetwork.isConnected()) {
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            connType = STATUS_MOBILENET_AVAILABLE;
                            callback(connType);
                        }
                    } else {
                        connType = STATUS_NETWORK_UNAVAILABLE;
                        callback(connType);
                    }
                } else {
                    connType = STATUS_NETWORK_UNAVAILABLE;
                    callback(connType);
                }
            }
        }
    }

    private void callback(int connType) {
        if (networkListener != null && lastConnType != connType) {
            switch (connType) {
                case STATUS_WIFI_AVAILABLE:
                    log.d("当前WiFi连接可用");
                    networkListener.onWifiAvailable();
                    break;
                case STATUS_MOBILENET_AVAILABLE:
                    log.d("当前移动网络连接可用");
                    networkListener.onMobileNetAvailable();
                    break;
                case STATUS_NETWORK_UNAVAILABLE:
                    log.d("当前没有网络连接");
                    networkListener.onNetworkUnavailable();
                    break;
            }
            lastConnType = connType;
        }
    }

    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        mContext.registerReceiver(netWorkChangeReceiver, filter);
    }

    private static class SingletonHolder {
        public final static NetworkManager instance = new NetworkManager();
    }

    public static NetworkManager getInstance() {
        return NetworkManager.SingletonHolder.instance;
    }

    public void init(Context context) {
        mContext = context;
        connManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        registerNetworkReceiver();
    }

    public void setNetworkChangeListener(OnNetworkStatusChangeListener listener) {
        networkListener = listener;
    }

    public void destroy() {
        networkListener = null;
    }

    /**
     * 判断当前网络是否是移动网络
     * @return true : 移动网络
     */
    public boolean isMobileNet() {
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是 wifi 网络
     * @return true : wifi 网络
     */
    public boolean isWifiNet() {
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有网络连接
     * @return true : 有网络
     */
    public boolean isNetworkConnected() {
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空
        if (networkInfo != null)
            return networkInfo.isAvailable();
        return false;
    }

    /**
     * 是否能与外网通信，有网络连接不代表一定能访问外网，此方法可以甄别
     * @return true : yes
     */
    public boolean isNetCommunication() {
        try {
            String ip = "www.baidu.com";// ping 地址
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                return true;
            } else {
            }
        } catch (Exception e) {
        }
        return false;

    }

    public interface OnNetworkStatusChangeListener {

        /**
         * wifi 网络可用
         */
        void onWifiAvailable();

        /**
         * 移动网络可用
         */
        void onMobileNetAvailable();

        /**
         * 无网络可用
         */
        void onNetworkUnavailable();

    }
}
