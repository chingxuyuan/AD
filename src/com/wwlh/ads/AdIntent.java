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
		int type  = $T(advertInfo.getTargetURL());	
		if (type == Advert_Type_Apk) {
			ApkDownloader apkDownloader  = new ApkDownloader(context);
			apkDownloader.dowanLoadApk(advertInfo);
		} else if (type == Advert_Type_Web) {
			
			String webUrl = $W(advertInfo.getTargetURL());
			 Intent it = new Intent(Intent.ACTION_VIEW,
			 Uri.parse(webUrl));
			 context.startActivity(it);
		}

	}
	
	
	public int $T(String tagUrl){
		if(tagUrl.endsWith(".apk") || tagUrl.endsWith(".APK")){
			return Advert_Type_Apk;
		}
		return Advert_Type_Web;
		
	}
	
	public String $W(String tagUrl){
		
		if(tagUrl.startsWith("http")){
			
		}else{
			return "http://"+tagUrl;
		}
		return "http://www.baidu.com";
	}


}
