package com.wwlh.ads.http;

import com.wwlh.ads.App;
import com.wwlh.ads.entity.AdvertInfo;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;

public class ApkDownloader {

	private Context context;

	private DownloadCompleteReceiver receiver;
	private long downId = 0;

	public ApkDownloader(Context context) {
		super();
		this.context = context;
	}

	

	/**
	 * 调用DownLoadManager下载apk
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void dowanLoadApk(AdvertInfo advertInfo) {
		
		if(advertInfo == null){
			return;
		}
		String packageName = advertInfo.getPackageName();
		String apkUrl = App.URL_APK + advertInfo.getTargetURL();
		if (receiver == null) {
			receiver = new DownloadCompleteReceiver();
			context.registerReceiver(receiver, new IntentFilter(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		}

		String serviceString = Context.DOWNLOAD_SERVICE;
		DownloadManager downloadManager;
		downloadManager = (DownloadManager) context
				.getSystemService(serviceString);
		Uri uri = Uri
				.parse(apkUrl);
		DownloadManager.Request request = new Request(uri);
		request.setMimeType("application/vnd.android.package-archive");
		// 禁止发出通知，既后台下载
		// request.setShowRunningNotification(false);
		// 不显示下载界面
		// request.setVisibleInDownloadsUi(false);
		request.setDestinationInExternalPublicDir(
				Environment.DIRECTORY_DOWNLOADS, "/wwlh/"+advertInfo.getTargetURL());
		long refernece = downloadManager.enqueue(request);
		SharedPreferences sp = context.getSharedPreferences("addownloadid", 0);
		sp.edit().putLong("addownloadid", refernece).commit();

	}

	/**
	 * 接受下载完成后打开apk
	 * 
	 * @author c
	 */
	class DownloadCompleteReceiver extends BroadcastReceiver {

		@SuppressLint("NewApi")
		@Override
		public void onReceive(Context ctx, Intent intent) {
			if (intent.getAction().equals(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				long downId = intent.getLongExtra(
						DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				SharedPreferences sp = ctx.getSharedPreferences("addownloadid",
						0);

				long refernece = sp.getLong("addownloadid", 0);

				if (refernece == downId) {

					String serviceString = Context.DOWNLOAD_SERVICE;

					DownloadManager dManager = (DownloadManager) context
							.getSystemService(serviceString);

					Intent install = new Intent(Intent.ACTION_VIEW);

					Uri downloadFileUri = dManager
							.getUriForDownloadedFile(downId);

					install.setDataAndType(downloadFileUri,
							"application/vnd.android.package-archive");

					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					context.startActivity(install);

				}

			}

			if (receiver != null) {
				context.unregisterReceiver(receiver);
			}

		}
	}
}
