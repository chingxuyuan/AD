package com.wwlh.ads.http;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.cxy.clib.CNet;
import com.google.gson.Gson;
import com.wwlh.ads.App;
import com.wwlh.ads.entity.AdvertInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;

/**
 * 
 * @author c
 * 
 */
public class AdRequest {

	private Context context;
	private CNet net;
	private String url = App.URL_PREFIX + "/sdk/randAdvert";
	private RespLisener respLisener;

	public AdRequest(Context context, RespLisener respLisener) {
		super();
		this.context = context;
		net = new CNet(context, handleCallBack);
		this.respLisener = respLisener;
	}

	public void request(Map<String, String> params) {
		net.request(url, params, 123);
	}

	/**
	 * 工作子线程回调函数,负责接收网络返回数据，并交给解析函数进一步处理
	 */
	private Callback handleCallBack = new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				parse(msg);// 仍然在子線程處理
				break;
			}
			return false;
		}
	};

	/**
	 * 解析返回的信息
	 * 
	 * @param msg
	 */
	public void parse(Message msg) {
		
		
		String str = msg.obj.toString();

		str = str.replace("\\n", "");

		str = str.replace("\\", "");
		str = str.replace("\"", "\'");
		str = str.substring(1, str.length() - 1);

		// str
		// ="{'id':16,'name':'a23','type':0,'createTime':'2016-05-3120:36:29','price':0.0,'enable':false,'system':false,'replace':false,'targetURL':'w23','showCount':0,'showInterval':0}";
		Gson gson = new Gson();
		
		Log.i("ad_json", str);
		AdvertInfo advert = null;
		try {
			advert = gson.fromJson(str, AdvertInfo.class);
		} catch (Exception e) {
			
			Log.i("ad_json", e.getMessage());
			return;
		}
		
		final AdvertInfo fadvert = advert;

		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				respLisener.resp(fadvert);// 這裡交給主線程處理
			}
		});

	}

	public interface RespLisener {
		public void resp(AdvertInfo advert);
	}
}
