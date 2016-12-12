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

/**
 * Created by hitomi on 2016/12/12.
 */

public class NetworkManager {

    private static final int STATUS_NETWORK_UNAVAILABLE = -100;
    private static final int STATUS_WIFI_AVAILABLE = 100;
    private static final int STATUS_MOBILENET_AVAILABLE = 101;

    private Logger log = XLog.tag("NetworkManager").build();
    private Context mContext;
    private OnNetworkStatusChangeListener networkListener;

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
                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
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
        mContext.registerReceiver(new NetWorkChangeReceiver(), filter);
    }

    private static class SingletonHolder {
        public final static NetworkManager instance = new NetworkManager();
    }

    public static NetworkManager getInstance() {
        return NetworkManager.SingletonHolder.instance;
    }

    public void init(Context context) {
        mContext = context;
        registerNetworkReceiver();
    }

    public void setNetworkChangeListener(OnNetworkStatusChangeListener listener) {
        networkListener = listener;
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
