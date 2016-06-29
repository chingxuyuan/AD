package com.wwlh.ads.activity;

import com.google.gson.JsonObject;
import com.wwlh.ads.R;
import com.wwlh.ads.SplashAd;
import com.wwlh.ads.SplashAdListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.Toast;

public class AdActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		
		
		AdActivity.this.startActivity(new Intent(AdActivity.this,
				MainActivity.class));
		this.finish();
		
		/*
		 * 开屏广告
		SplashAdListener splashAdListener = new SplashAdListener() {
			@Override
			public void onAdClick(JsonObject info) {
			}

			@Override
			public void onDismissed() {
				AdActivity.this.startActivity(new Intent(AdActivity.this,
						MainActivity.class));
			}
		};
		SplashAd splashAd = new SplashAd(this,splashAdListener);
		
		*/
		
	}

}
