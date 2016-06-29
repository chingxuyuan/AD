package com.wwlh.ads;

import com.wwlh.ads.entity.PushMessage;

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

	public void notify(String title, String content) {
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
				Log.i("PushClient", "BroadcastReceiver");
			}
		};
		IntentFilter filter = new IntentFilter("wwlh.push.notification");
		context.registerReceiver(receiver, filter);
		Intent intent = new Intent();
		intent.setAction("wwlh.push.notification");
		PendingIntent resultPendingIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}

	public void notify(PushMessage push) {
		String title = push.getTopic();
		String content = push.getContent();
		notify(title,content);
	}

}
