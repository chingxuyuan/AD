package com.wwlh.ads;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.AdRequest.RespLisener;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.util.NetUtil;
import com.wwlh.ads.util.WindowInfo;

public class AdView {

	
	private RelativeLayout parent;
	
	private RelativeLayout container;

	private Context context;

	private AdvertInfo advertInfo;
	
	private HashMap<String, String> params;
	
	private AdRequest adRequest = null;

	private AdViewListener adViewListener;
	
	
	private Handler handler = new Handler( );
	
	
	private ImgDownloader down = null;
	
	private boolean showToast = true;
	
	private long millis = 5000;
	/**
	 * 构造一个广告控件，自动刷新
	 * @param context 上下文对象
	 * @param parent 指定父布局，类型为RelativeLayout
	 */
	public AdView(Context context,RelativeLayout parent) {
		this.context = context;
		this.parent = parent;
		initAdRequest();
		refresh();
	}
	
	/**
	 * 实例化一个底部横幅广告
	 * @param ctx
	 * @param parent 指定父布局，类型为RelativeLayout
	 * @return
	 */
	public static AdView instance(Context ctx,RelativeLayout parent){
		return new AdView(ctx,parent);
	}
	
	
	/**
	 * 初始化广告网络请求
	 */
	private void initAdRequest(){
		down = new ImgDownloader(context);
		RespLisener listener = new AdRequest.RespLisener(){
			@Override
			public void resp(AdvertInfo advert) {
				advertInfo = advert;
				initContainer();
			}
		};
		adRequest = new AdRequest(context,listener);
		params = new HashMap<String, String>();
		params.put("type", App.Advert_Type_Banner+"");
	}
	
	/**
	 * 刷新广告
	 */
	private void refresh(){
		
		if(context == null){
			return;
		}else if(((Activity) context).isFinishing()){
			return;
		}
		requestAdvert();
		handler.postDelayed(new Runnable(){
			@Override
			public void run() {
				refresh();
			}
		}, millis);
	}
	
	/**
	 * 请求一条广告并添加到广告布局中
	 */
	private void requestAdvert(){
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
			}
		});

		int mp = RelativeLayout.LayoutParams.MATCH_PARENT;
		RelativeLayout.LayoutParams rllp = null;
		rllp = new RelativeLayout.LayoutParams(mp, mp);
		container.addView(imgAd, rllp);
		attachParent();
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
	

	/**
	 * 从网络缓存中异步加载广告图片
	 * 
	 * @param imgView
	 */
	private void loadImage(ImageView imgView) {
		down.load(imgView, advertInfo);
	}

	

	
	/**
	 * 设置刷新广告间隔时间
	 * @param millis 间隔毫秒数
	 */
	public void setIntervals(long millis){
		this.millis = millis;
	}
	
	
	/**
	 * 设置是否显示应用下载toast提示
	 * @param show
	 */
	public void setShowToast(boolean show){
		showToast = show;
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
