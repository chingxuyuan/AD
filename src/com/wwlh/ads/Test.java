package com.wwlh.ads;

import android.os.Message;

import com.google.gson.Gson;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;

public class Test {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "{'id':16,'name':'23','type':0,'createTime':'May 31, 2016 8:36:29 PM','price':0.0,'enable':false,'system':false,'replace':false,'targetURL':'w23','showCount':0,'showInterval':0}";
		Gson gson = new Gson();
		AdvertInfo advert = new AdvertInfo();
		advert.setResourceURL("rrrrr");
		
		String js = gson.toJson(advert);
		
		
		
		AdvertInfo aaa = gson.fromJson(js, AdvertInfo.class);
		
		String url =aaa.getResourceURL();
		
		System.out.println(url);

	}

}
