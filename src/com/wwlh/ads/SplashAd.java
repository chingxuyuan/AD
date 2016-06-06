package com.wwlh.ads;

import java.util.HashMap;

import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.AdRequest.RespLisener;
import com.wwlh.ads.util.Share;
import com.wwlh.ads.view.AdImage;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashAd {

	private Context context;
	private SplashAdListener splashAdListener;

	private RelativeLayout rlytAd;

	private AdvertInfo advert;

	private Share share = null;

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
			if(splashAdListener!=null){
				splashAdListener.onDismissed();
			}
		} else {
			initView();
		}
		
		/**
		 * 一秒后刷新下一次显示的广告
		 */
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				refresh();
			}}, 1500);
		
		/**
		 * 5秒后跳转
		 */
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				splashAdListener.onDismissed();
				((Activity) SplashAd.this.context).finish();
			}}, 1500);
		
		
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
		imgAd.init(advert, splashAdListener);
		
		rlytAd.addView(imgAd, rllp);
		ViewGroup vg = (ViewGroup) ((Activity) context).getWindow()
				.getDecorView();
		vg.addView(rlytAd, rllp);

	}

	/**
	 * 获取本地下载的广告内容
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

	public void setSplashAdListener(SplashAdListener splashAdListener) {
		this.splashAdListener = splashAdListener;
	}

	
	
}
