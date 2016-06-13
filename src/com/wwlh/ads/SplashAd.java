package com.wwlh.ads;

import java.util.HashMap;

import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.AdRequest.RespLisener;
import com.wwlh.ads.util.NetUtil;
import com.wwlh.ads.util.Share;
import com.wwlh.ads.view.AdImage;
import com.wwlh.ads.view.JumpLayout;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashAd {

	private Context context;
	private SplashAdListener splashAdListener;

	private RelativeLayout rlytAd;

	private AdvertInfo advert;

	private Share share = null;

	private long Duration = 3000;

	/**
	 * 构造一个广告控件，自动刷新
	 * 
	 * @param context
	 *            上下文对象
	 * @param parent
	 *            指定父布局，类型为RelativeLayout
	 */
	public SplashAd(Context context) {
		this.context = context;
		share = new Share(context);
		advert = getLocalHistory();

		if (advert == null || advert.getResourceURL() == null) {
			if (splashAdListener != null) {
				splashAdListener.onDismissed();
			}
		} else {
			initView();
		}

		/**
		 * 一秒后刷新下一次显示的广告
		 */
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		}, 1000);

		/**
		 * 5秒后跳转
		 */
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
			}
		}, Duration);
	}

	/**
	 * 创建广告界面
	 */
	private void initView() {
		rlytAd = new RelativeLayout(context);
		int mp = RelativeLayout.LayoutParams.MATCH_PARENT;
		RelativeLayout.LayoutParams rllp = null;
		rllp = new RelativeLayout.LayoutParams(mp, mp);
		AdImage imgAd = new AdImage(context);
		imgAd.setADImageListener(new AdImage.AdImageListener() {
			@Override
			public void onError() {
				Log.i("SplashAd", "图片加载失败，");
				dismiss();
			}
		});
		imgAd.init(advert, splashAdListener);
		
		rlytAd.addView(imgAd, rllp);

		JumpLayout jumpLayout = new JumpLayout(context);
		RelativeLayout.LayoutParams tp = null;
		tp = new RelativeLayout.LayoutParams(100, 60);
		tp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		tp.rightMargin = 50;
		tp.topMargin = 50;
		rlytAd.addView(jumpLayout, tp);

		ViewGroup vg = (ViewGroup) ((Activity) context).getWindow()
				.getDecorView();
		vg.addView(rlytAd, rllp);
		
		
		jumpLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

	}

	
	/**
	 * 调用跳转接口，回收图片占用的内存
	 */
	private void dismiss() {
		if(splashAdListener == null){
			return;
		}
		if (rlytAd != null) {
			ImageView imgAd = (ImageView) rlytAd.findViewWithTag("img");
			if (imgAd != null) {
				BitmapDrawable drawable = (BitmapDrawable) imgAd.getDrawable();
				if(drawable!=null){
					Bitmap bmp = drawable.getBitmap();
					if (null != bmp && !bmp.isRecycled()) {
						bmp.recycle();
						bmp = null;
					}
				}
			}
		}
		
		splashAdListener.onDismissed();
		splashAdListener = null;
		

		((Activity) SplashAd.this.context).finish();
		

	}

	/**
	 * 获取本地下载的广告内容
	 * 
	 * @return
	 */
	private AdvertInfo getLocalHistory() {
		return share.getAdvertInfo();
	}

	/**
	 * 刷新一条广告，下次显示
	 */
	private void refresh() {

		RespLisener listener = new AdRequest.RespLisener() {
			@Override
			public void resp(AdvertInfo advert) {
				share.save(advert);
			}
		};
		AdRequest adRequest = new AdRequest(context, listener);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", App.Advert_Type_Splash + "");
		adRequest.request(params);
	}

	/**
	 * 设置广告延迟时间
	 * 
	 * @param duration
	 */
	public void setDuration(long duration) {
		Duration = duration;
	}

	public void setSplashAdListener(SplashAdListener splashAdListener) {
		this.splashAdListener = splashAdListener;
	}

}
