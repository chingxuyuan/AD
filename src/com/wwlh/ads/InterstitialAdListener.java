package com.wwlh.ads;

import com.google.gson.JsonObject;

public interface InterstitialAdListener{
	/**
	 * 点击广告
	 * @param info
	 */
	public void onAdClick(JsonObject info);
}
