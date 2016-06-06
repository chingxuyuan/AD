package com.wwlh.ads.view;

import com.google.gson.JsonObject;
import com.wwlh.ads.AdIntent;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.http.ImgDownloader;
import com.wwlh.ads.interfaces.IAdListener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class AdImage extends ImageView {

	private Context context;
	private IAdListener adListener;
	private AdvertInfo advertInfo;

	ImgDownloader down = null;

	public AdImage(Context context) {
		super(context);
		this.context = context;
		down = new ImgDownloader(context);
	}

	public void init(AdvertInfo advertInfo, IAdListener adListener) {
		this.adListener = adListener;
		this.advertInfo = advertInfo;
		this.setTag("img");
		this.setScaleType(ImageView.ScaleType.FIT_XY);
		down.load(this, advertInfo);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (event.getAction() == MotionEvent.ACTION_UP) {
			intent();
			this.setEnabled(false);
		}

		return super.onTouchEvent(event);
	}

	private void intent() {
		if (adListener != null) {
			JsonObject json = new JsonObject();
			json.addProperty("name", advertInfo.getName());
			json.addProperty("packageName", advertInfo.getPackageName());
			json.addProperty("desc", advertInfo.getMemo());
			if (advertInfo.getTargetURL().endsWith(".apk")
					|| advertInfo.getTargetURL().endsWith(".APK")) {
				json.addProperty("type", "apk");

				Toast.makeText(context,
						"你点击的" + advertInfo.getName() + "正在下载。。。",
						Toast.LENGTH_LONG).show();
			} else {
				json.addProperty("type", "url");
			}
			adListener.onAdClick(json);
		}

		// 跳转到广告页面
		AdIntent intent = new AdIntent(context);
		intent.start(advertInfo);

	}

}
