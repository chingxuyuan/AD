package com.wwlh.ads.interfaces;

import com.google.gson.JsonObject;

public interface IAdListener {
	/**
	 * 点击广告
	 * @param info
	 */

	public void onAdClick(JsonObject info);
}
