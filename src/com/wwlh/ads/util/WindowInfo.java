package com.wwlh.ads.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class WindowInfo {

	public static int screenWidth;

	public static int[] $screen(Context ctx) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		return new int[] { screenWidth, screenHeight };
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static int calcAdHeight(Context ctx) {
		int[] screen = $screen(ctx);
		if(isPortrait(ctx)){
			return (int) (screen[0] / 6);
		}else{
			return (int) (screen[1] / 6);
		}
		
		
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static int $width(Context ctx) {
		int screenWidth = $screen(ctx)[0];
		return screenWidth;
	}

	
	/**
	 * 判断竖屏模式
	 * @param context
	 * @return
	 */
	public static boolean isPortrait(Context context) {
		
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

}
