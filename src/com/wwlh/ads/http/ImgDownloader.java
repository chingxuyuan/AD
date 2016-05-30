package com.wwlh.ads.http;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cxy.clib.CCache;
import com.wwlh.ads.R;
import com.wwlh.ads.entity.AdvertInfo;

public class ImgDownloader {
	private Context context;
	private RequestQueue queue = null;

	public ImgDownloader(Context context) {
		super();
		this.context = context;
		queue = Volley.newRequestQueue(context);
	}

	public void load(ImageView imgView, AdvertInfo advert) {
		CCache imageCache = CCache.instance(context);
		ImageLoader loader = new ImageLoader(queue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(imgView, R.drawable.t, R.drawable.ic_launcher);
		String url = null;
		if (advert != null) {
			url = advert.getImageUrl();
		}
		url = "http://file01.16sucai.com/d/file/2011/0824/20110824050707181.jpg";
		loader.get(url, listener);
	}

}
