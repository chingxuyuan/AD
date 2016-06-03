package com.wwlh.ads.http;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cxy.clib.CCache;
import com.wwlh.ads.App;
import com.wwlh.ads.R;
import com.wwlh.ads.entity.AdvertInfo;

public class ImgDownloader {
	private Context context;
	private RequestQueue queue = null;

	public ImgDownloader(Context context) {
		super();
		this.context = context;
		if(queue == null){
			queue = Volley.newRequestQueue(context);
		}
		
	}

	public void load(ImageView imgView, AdvertInfo advert) {
		CCache imageCache = CCache.instance(context);
		ImageLoader loader = new ImageLoader(queue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(imgView, R.drawable.t, R.drawable.ic_launcher);
		String url = null;
		if (advert != null) {
			url = App.URL_IMG + advert.getResourceURL();
		}
		Log.i("img url", url);
		loader.get(url, listener);
	}

}
