package com.wwlh.ads.util;
import android.app.Activity;
import android.content.Context;
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
	public static int calcAdHeight(Context ctx){
		int screenWidth = $screen(ctx)[0];
		return (int) (screenWidth/6);
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public static int $width(Context ctx){
		int screenWidth = $screen(ctx)[0];
		return screenWidth;
	}
	

}
