package com.wwlh.ads;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.AdRequest;
import com.wwlh.ads.http.AdRequest.RespLisener;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.util.BitmapUtil;
import com.wwlh.ads.util.NetUtil;
import com.wwlh.ads.util.WindowInfo;

public class InterstitialAd {

	private RelativeLayout container;

	private PopupWindow ppw = null;

	private Context context;

	private AdvertInfo advertInfo;

	private HashMap<String, String> params;

	private AdRequest adRequest = null;

	private InterstitialAdListener adViewListener;

	private Handler handler = new Handler();

	private ImgDownloader down = null;

	private boolean showToast = true;

	private long millis = 1000;

	/**
	 * 构造一个广告控件，自动刷新
	 * 
	 * @param context
	 *            上下文对象
	 * @param parent
	 *            指定父布局，类型为RelativeLayout
	 */
	public InterstitialAd(Context context) {
		this.context = context;
		initAdRequest();
		refresh();
	}

	/**
	 * 初始化广告网络请求
	 */
	private void initAdRequest() {
		down = new ImgDownloader(context);
		RespLisener listener = new AdRequest.RespLisener() {
			@Override
			public void resp(AdvertInfo advert) {
				advertInfo = advert;
				initContainer();
			}
		};
		adRequest = new AdRequest(context, listener);
		params = new HashMap<String, String>();
		params.put("type", App.Advert_Type_Interstitial + "");
	}

	/**
	 * 刷新广告
	 */
	private void refresh() {

		if (context == null) {
			return;
		} else if (((Activity) context).isFinishing()) {
			return;
		}
		requestAdvert();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		}, millis);
	}

	/**
	 * 显示广告
	 */
	public void show() {
		if (ppw != null) {
			dismiss();
			View cv = ((Activity) context).getWindow().getDecorView();
			ppw.showAtLocation(cv, Gravity.CENTER, 0, 0);
		}
	}

	private void dismiss() {
		if (ppw != null) {
			ppw.dismiss();
		}
	}

	/**
	 * 请求一条广告并添加到广告布局中
	 */
	private void requestAdvert() {
		adRequest.request(params);
	}

	/**
	 * 广告容器，加载横幅广告
	 */
	private void initContainer() {
		if (container != null) {
			ImageView imgAd = (ImageView) container.findViewWithTag("img");
			loadImage(imgAd);
			return;
		}
		initDialog();

	}

	/**
	 * 将banner广告布局添加到父布局中
	 * 
	 * @param parent
	 *            指定父布局，类型为RelativeLayout
	 */

	@SuppressLint("ResourceAsColor") private void initDialog() {
		/*
		 * 先建一张图片
		 */
		ImageView imgAd = new ImageView(context);
		imgAd.setTag("img");
		imgAd.setBackgroundColor(Color.RED);
		imgAd.setId(101);
		imgAd.setScaleType(ImageView.ScaleType.FIT_XY);
		ImageView imgClose = new ImageView(context);
		imgClose.setTag("imgClose");
		
		imgClose.setImageBitmap(BitmapUtil.getCloseIcon(context));
		
		imgClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		imgAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();

				if (adViewListener != null) {
					JsonObject json = new JsonObject();

					json.addProperty("name", advertInfo.getName());

					json.addProperty("packageName", advertInfo.getPackageName());

					json.addProperty("desc", advertInfo.getMemo());

					if (advertInfo.getTargetURL().endsWith(".apk")
							|| advertInfo.getTargetURL().endsWith(".APK")) {
						json.addProperty("type", "apk");
					} else {
						json.addProperty("type", "url");
					}
					adViewListener.onAdClick(json);
				}

				toastApkDownload();
				// 跳转到广告页面
				AdIntent intent = new AdIntent(context);
				intent.start(advertInfo);
			}
		});

		/*
		 * 添加到Relativelayout
		 */
		int wc = RelativeLayout.LayoutParams.WRAP_CONTENT;
		RelativeLayout.LayoutParams rllp = null;

		int width = WindowInfo.$width(context);

		rllp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
		container = new RelativeLayout(context);
		container.addView(imgAd, rllp);

		
		RelativeLayout.LayoutParams rllpClose = null;
		rllpClose = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rllpClose.addRule(RelativeLayout.ALIGN_RIGHT,101);
		rllpClose.addRule(RelativeLayout.ALIGN_TOP,101);
		imgClose.setPadding(50, 10, 10, 50);
		container.addView(imgClose, rllpClose);
		

		ppw = new PopupWindow(context);
		ppw.setWidth(width);
		ppw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		ppw.setContentView(container);
		ppw.setBackgroundDrawable(null);
		ppw.setClippingEnabled(true);

	
		
		loadImage(imgAd);

	}

	/**
	 * 从网络缓存中异步加载广告图片
	 * 
	 * @param imgView
	 */
	private void loadImage(ImageView imgView) {
		down.load(imgView, advertInfo);
	}

	private void toastApkDownload() {
		if (showToast == false) {
			return;
		}

		String url = advertInfo.getTargetURL();
		if (url.endsWith(".apk")) {
			Toast.makeText(context, "你点击的" + advertInfo.getName() + "正在下载。。。",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 设置刷新广告间隔时间
	 * 
	 * @param millis
	 *            间隔毫秒数
	 */
	public void setIntervals(long millis) {
		this.millis = millis;
	}

	/**
	 * 设置是否显示应用下载toast提示
	 * 
	 * @param show
	 */
	public void setShowToast(boolean show) {
		showToast = show;
	}

	public AdvertInfo getAdvertInfo() {
		return advertInfo;
	}

	public void setAdvertInfo(AdvertInfo advertInfo) {
		this.advertInfo = advertInfo;
	}

	public void setAdViewListener(InterstitialAdListener adViewListener) {
		this.adViewListener = adViewListener;
	}

}
