package com.wwlh.ads.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.wwlh.ads.entity.AdvertInfo;

public class Share {

	private Context context;
	private final String Advert_File = "wwlh_advert";
	private final String Advert_Key = "json_advert";

	public Share(Context context) {
		super();
		this.context = context;
	}

	public void save(AdvertInfo advert) {
		String json = new Gson().toJson(advert);
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Advert_File, Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(Advert_Key, json);
		editor.commit();// 提交修改
	}

	public AdvertInfo getAdvertInfo() {
		SharedPreferences share = context.getSharedPreferences(Advert_File,
				Context.MODE_PRIVATE);
		String json = share.getString(Advert_Key, "");
		Gson gson = new Gson();
		AdvertInfo advert = null;
		try {
			advert = gson.fromJson(json, AdvertInfo.class);
		} catch (Exception e) {
			return null;
		}
		return advert;
	}

}
