package com.wwlh.ads;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.util.WindowInfo;

public class AdView {

	private RelativeLayout container;

	private Context context;

	private AdvertInfo advertInfo;

	private AdViewListener adViewListener;

	public AdView(Context context) {
		this.context = context;
		initContainer();
	}

	/**
	 * 广告容器，加载横幅广告
	 */
	private void initContainer() {
		container = new RelativeLayout(context);
		ImageView imgAd = new ImageView(context);
		imgAd.setScaleType(ImageView.ScaleType.FIT_XY);
		loadImage(imgAd);
		imgAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (adViewListener != null) {
					adViewListener.onAdClick(null);
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
	public void attachParent(RelativeLayout parent) {
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
