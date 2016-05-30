
package com.wwlh.ads.http;
import org.json.JSONException;
import org.json.JSONObject;

import com.cxy.clib.CNet;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.os.Handler.Callback;

/**
 * 
 * @author c
 *
 */
public class AdRequest {

	
	private Context context;
	private CNet net;
	private String url = "http://www.weather.com.cn/adat/sk/101280601.html";
	private RespLisener respLisener;
	public AdRequest(Context context,RespLisener respLisener) {
		super();
		this.context = context;
		net = new CNet(context,handleCallBack);
		this.respLisener = respLisener;
	}
	
	
	public void request(){
		net.request(url, 123);
	}
	
	/**
	 * 工作子线程回调函数,负责接收网络返回数据，并交给解析函数进一步处理
	 */
	private Callback handleCallBack = new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				parse(msg);//仍然在子線程處理
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
		try {
			JSONObject json = new JSONObject(msg.obj.toString());
			JSONObject info = json.getJSONObject("weatherinfo");
			String city = info.getString("city");
			String temp = info.getString("temp");
			String WD = info.getString("WD");
			final String desc = city+ " 温度："+temp+" "+WD;
			
			
			((Activity)context).runOnUiThread(new Runnable(){
				@Override
				public void run() {
					respLisener.resp(desc);//這裡交給主線程處理
				}
			});
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	interface  RespLisener{
		public void resp(String weather);
	}
}
