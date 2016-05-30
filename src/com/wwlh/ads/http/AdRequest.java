package com.wwlh.ads.http;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.cxy.clib.CCache;
import com.wwlh.ads.entity.AdvertInfo;

import android.content.Context;
import android.widget.ImageView;

public class AdRequest {
	private Context context;
	private RequestQueue queue;
	public AdRequest(Context context) {
		super();
	    this.context = context;
	    queue = Volley.newRequestQueue(context);
	   
	}

	public void load(ImageView imgView,AdvertInfo advert){
		CCache imageCache = CCache.instance(context);
		ImageLoader loader = new ImageLoader(queue, imageCache);
		ImageListener listener = ImageLoader.getImageListener(imgView,0, 0);
		String url = advert.getImageUrl();
		url = "https://www.baidu.com/img/bd_logo1.png";
		loader.get(url, listener);
	}

}
