package com.wwlh.ads;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.AdRequest.RespLisener;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.util.WindowInfo;

public class AdView {

	
	private RelativeLayout parent;
	
	private RelativeLayout container;

	private Context context;

	private AdvertInfo advertInfo;

	private AdViewListener adViewListener;
	
	
	/**
	 * 构造一个广告控件，自动
	 * @param context 上下文对象
	 * @param parent 指定父布局，类型为RelativeLayout
	 */
	public AdView(Context context,RelativeLayout parent) {
		
		this.context = context;
		
		this.parent = parent;
		
		
		req();
		
	}
	
	
	public void req(){
		requestAdvert();
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				req();
				
			}
		}, 3000);
	}
	
	/**
	 * 请求一条广告并添加到广告布局中
	 */
	private void requestAdvert(){
		
		RespLisener listener = new AdRequest.RespLisener(){
			@Override
			public void resp(AdvertInfo advert) {
				advertInfo = advert;
				initContainer();
			}
		};
		AdRequest adRequest = new AdRequest(context,listener);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", App.Advert_Type_Banner+"");
		adRequest.request(params);
	}


	/**
	 * 广告容器，加载横幅广告
	 */
	private void initContainer() {
		
		
		if(container!=null){
			ImageView imgAd = (ImageView) container.findViewWithTag("img");
			loadImage(imgAd);
			
			return;
		}
		container = new RelativeLayout(context);
		ImageView imgAd = new ImageView(context);
		imgAd.setTag("img");
		imgAd.setScaleType(ImageView.ScaleType.FIT_XY);
		loadImage(imgAd);
		imgAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (adViewListener != null) {
					JsonObject json = new JsonObject();
					
					json.addProperty("name", advertInfo.getName());
					
					json.addProperty("packageName", advertInfo.getPackageName());
					
					json.addProperty("desc", advertInfo.getMemo());
					
					if(advertInfo.getTargetURL().endsWith(".apk") || advertInfo.getTargetURL().endsWith(".APK")){
						json.addProperty("type", "apk");
					}else{
						json.addProperty("type", "url");
					}
					
					
					adViewListener.onAdClick(json);
				}
				//跳转到广告页面
				AdIntent intent = new AdIntent(context);
				intent.start(advertInfo);
				
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run() {
						requestAdvert();
						
					}
				}, 1000);
			}
		});

		int mp = RelativeLayout.LayoutParams.MATCH_PARENT;
		RelativeLayout.LayoutParams rllp = null;
		rllp = new RelativeLayout.LayoutParams(mp, mp);
		container.addView(imgAd, rllp);
		attachParent();
	}
	
	
	

	/**
	 * 从网络缓存中异步加载广告图片
	 * 
	 * @param imgView
	 */
	private void loadImage(ImageView imgView) {
		ImgDownloader down = new ImgDownloader(context);
		down.load(imgView, advertInfo);
	}

	/**
	 * 将banner广告布局添加到父布局中
	 * 
	 * @param parent
	 *            指定父布局，类型为RelativeLayout
	 */
	private void attachParent() {
		int width = RelativeLayout.LayoutParams.MATCH_PARENT;
		int height = WindowInfo.calcAdHeight(context);
		RelativeLayout.LayoutParams rllp = null;

		rllp = new RelativeLayout.LayoutParams(width, height);

		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		parent.addView(container, rllp);
	}

	public AdvertInfo getAdvertInfo() {
		return advertInfo;
	}

	public void setAdvertInfo(AdvertInfo advertInfo) {
		this.advertInfo = advertInfo;
	}

	public void setAdViewListener(AdViewListener adViewListener) {
		this.adViewListener = adViewListener;
	}

}
