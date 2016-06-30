package com.wwlh.ads.http;

import java.util.HashMap;

import com.wwlh.ads.App;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.util.ApkUtil;

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
import android.util.Log;
import android.widget.Toast;

public class ApkDownloader {

	private Context context;

	private DownloadCompleteReceiver receiver;
	private long downId = 0;
	private ApkUtil apkUtil;
	
	private boolean showToast = true;
	
	
	public ApkDownloader(Context context) {
		super();
		this.context = context;
		apkUtil = new ApkUtil(context);
		
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
		
		//根据包名判断应用是否安装
		String packageName = advertInfo.getPackageName();
		boolean isInstalled  = false;
		if(packageName!=null && packageName.length()>0){
			isInstalled = apkUtil.isApkInstalled(packageName);
			Log.i("ApkDownloader", "应用是否安装"+isInstalled);
		}
		if(isInstalled){
			apkUtil.openInsatlledApk(packageName);
			Log.i("ApkDownloader", "打开应用");
			return;
		}
		
		
		
		//下载应用
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
		sp.edit().putLong("addownloadid", refernece).putInt("advertid", advertInfo.getId()).commit();
		
		toastApkDownload(advertInfo.getName());

	}
	
	
	private void toastApkDownload(String name) {
		if (showToast == false) {
			return;
		}
			Toast.makeText(context, "你点击的应用" + name + "正在下载。。。",
					Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 设置是否显示应用下载toast提示
	 * @param show
	 */
	public void setShowToast(boolean show){
		showToast = show;
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
				
				int advertid = sp.getInt("advertid", 0);

				if (refernece == downId) {

					String serviceString = Context.DOWNLOAD_SERVICE;

					DownloadManager dManager = (DownloadManager) context
							.getSystemService(serviceString);
					
					Intent install = new Intent(Intent.ACTION_VIEW);

					Uri downloadFileUri = dManager
							.getUriForDownloadedFile(downId);

					
					//解析包名并发送到服务器
//					String pkg = apkUtil.getPakageByApk(downloadFileUri.getPath());
//					HashMap<String, String> hashMap = new HashMap<String, String>();
//					hashMap.put("id", advertid+"");
//					hashMap.put("packageName", pkg);
//					adRequest.updateAdvert(hashMap);
					
					
					//去安装
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
