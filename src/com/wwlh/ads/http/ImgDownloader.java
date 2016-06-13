package com.wwlh.ads.http;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cxy.clib.CCache;
import com.wwlh.ads.App;
import com.wwlh.ads.R;
import com.wwlh.ads.entity.AdvertInfo;
import com.wwlh.ads.view.AdImage.AdImageListener;

public class ImgDownloader {
	private Context context;
	private RequestQueue queue = null;

	private AdImageListener adImageListener = null;

	public ImgDownloader(Context context) {
		super();
		this.context = context;
		if (queue == null) {
			queue = Volley.newRequestQueue(context);
		}
	}

	public void load(ImageView imgView, AdvertInfo advert) {
		CCache imageCache = CCache.instance(context);
		ImageLoader loader = new ImageLoader(queue, imageCache);

		ImageListener listener = getImageListener(imgView, 0, 0);

		String url = null;
		if (advert != null) {
			url = App.URL_IMG + advert.getResourceURL();
		}
		Log.i("img url", url);
		loader.get(url, listener);
	}

	public void setAdImageListener(AdImageListener adImageListener) {
		this.adImageListener = adImageListener;
	}

	
	/**
	 * 重写了系统定义的监听
	 * @param view
	 * @param defaultImageResId
	 * @param errorImageResId
	 * @return
	 */
	public ImageListener getImageListener(final ImageView view,
			final int defaultImageResId, final int errorImageResId) {
		return new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				
				if (errorImageResId != 0) {
					view.setImageResource(errorImageResId);
				}
				
				if (adImageListener != null) {
					adImageListener.onError();
					Log.i("ImgDownloader", "VolleyError");
				}
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				
				if (response.getBitmap() != null) {
					view.setImageBitmap(response.getBitmap());
				} else if (defaultImageResId != 0) {
					view.setImageResource(defaultImageResId);
				}else{
					if (adImageListener != null) {
						adImageListener.onError();
						Log.i("ImgDownloader", "response 图片加载失败");
					}
				}
			}
		};
	}

}
