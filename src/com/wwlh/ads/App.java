package com.wwlh.ads;

public class App {
	
	public static final String URL_PREFIX = "http://192.168.5.161:8083";
	
	public static final String URL_IMG = URL_PREFIX+"/upload/image/";
	public static final String URL_APK = URL_PREFIX+"/upload/apk/";
	/**
	 * 广告类型
	 */
	public static final int Advert_Type_Banner = 0;//横幅广告
	public static final int Advert_Type_Interstitial = 1;//插屏广告
	public static final int Advert_Type_Splash = 2;//开屏广告
	public static final int Advert_Type_Notification = 3;//推送通知
	public static final int Advert_Type_Slient = 4;//静默安装
}
