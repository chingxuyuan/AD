package com.wwlh.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.ApkDownloader;

public class AdIntent {

	private Context context;

	public AdIntent(Context context) {
		super();
		this.context = context;
	}

	private static int Advert_Type_Apk = 1;
	private static int Advert_Type_Web = 0;

	
	/**
	 * 根据广告类型，选择跳转或下载apk
	 * @param advertInfo
	 */
	public void start(AdvertInfo advertInfo) {
		
		if (advertInfo == null) {
			return;
		}
		int type  = advertInfo.getType();	
		if (type == Advert_Type_Apk) {
			ApkDownloader apkDownloader  = new ApkDownloader(context);
			apkDownloader.dowanLoadApk(advertInfo);
		} else if (type == Advert_Type_Web) {
			 Intent it = new Intent(Intent.ACTION_VIEW,
			 Uri.parse(advertInfo.getAdvertInfoUrl()));
			 context.startActivity(it);
		}

	}


}
