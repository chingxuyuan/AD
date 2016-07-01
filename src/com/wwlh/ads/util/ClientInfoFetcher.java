
package com.wwlh.ads.util;


import com.wwlh.ads.entity.ClientInfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class ClientInfoFetcher {

	public static boolean isUseWifi (Context context) {

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}

		int type = info.getType();

		if (type == ConnectivityManager.TYPE_WIFI) {
			return true;
		}

		return false;
	}

	public static String getNetworkId (Context context) {

		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		int type = info.getType();

		if (type == ConnectivityManager.TYPE_WIFI) {
			return "WIFI";
		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			return "4G";
		} else {
			return null;
		}
	}

	public static String getMACAddress (Context context) {

		WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		String mac = null;

		if (mWifiManager.isWifiEnabled()) {
			WifiInfo WifiInfo = mWifiManager.getConnectionInfo();
			mac = WifiInfo.getMacAddress();
		}

		return mac;
	}

	public static boolean fetchBaseInfo (Context context, ClientInfo client) {

		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		client.setBrand(android.os.Build.BRAND);
		client.setModel(android.os.Build.MODEL);
		client.setIMEI(mTelephonyMgr.getDeviceId());
		client.setIMSI(mTelephonyMgr.getSubscriberId());

		return true;
	}
}
