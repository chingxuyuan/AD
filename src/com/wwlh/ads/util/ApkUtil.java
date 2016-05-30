package com.wwlh.ads.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ApkUtil {

	private Context context;

	public ApkUtil(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 根据本地apk获取包名
	 * 
	 * @param filePath
	 * @return
	 */
	public String getPakageByApk(String filePath) {

		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(filePath,
				PackageManager.GET_ACTIVITIES);
		ApplicationInfo appInfo = null;
		if (info != null) {
			appInfo = info.applicationInfo;
			String packageName = appInfo.packageName;
			return packageName;
		}
		return null;
	}

	/**
	 * apk是否安装过
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean isApkInstalled(String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * 打开已经安装的apk
	 * @param packageName
	 */
	public void openInsatlledApk(String packageName) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				packageName);
		context.startActivity(intent);
	}

}
