package com.wwlh.ads;

import java.util.HashMap;

import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.entity.PushMessage;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.http.AdRequest.RespLisener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class PushNotification {

	private Context context;

	public PushNotification(Context context) {
		super();
		this.context = context;
	}

	public void notify(PushMessage pushMessage) {
		
		int id = pushMessage.getAdvertId();
		String title = pushMessage.getTopic();
		String content = pushMessage.getContent();
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setContentText(content)
				.setAutoCancel(true);
		
		mBuilder.setTicker(title);
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				
				int id = intent.getIntExtra("id", -1);
				Log.i("PushClient", "onReceive: "+id);
				requestAdvert(id+"");
			}
		};
		IntentFilter filter = new IntentFilter("wwlh.push.notification");
		context.registerReceiver(receiver, filter);
		Intent intent = new Intent();
		intent.putExtra("id", id);
		intent.setAction("wwlh.push.notification");
		PendingIntent resultPendingIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	/**
	 * 刷新一条广告，下次显示
	 */
	private void requestAdvert(String id) {
		
		Log.i("PushClient", "刷新一条广告");
		
		RespLisener listener = new AdRequest.RespLisener() {
			@Override
			public void resp(AdvertInfo advertInfo) {
				Log.i("PushClient", "返回了一条广告: "+advertInfo.getName());
				//跳转到广告页面
				AdIntent intent = new AdIntent(context);
				intent.start(advertInfo);
			}
		};
		AdRequest adRequest = new AdRequest(context, listener);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		adRequest.requestAdvert(params);
	}


}
