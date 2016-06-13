package com.wwlh.ads;

import com.google.gson.JsonObject;

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
		SplashAd splashAd = new SplashAd(this);
		splashAd.setDuration(10000);
		splashAd.setSplashAdListener(new SplashAdListener() {
			
			@Override
			public void onAdClick(JsonObject info) {
				Toast.makeText(AdActivity.this, "点击", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onDismissed() {
				//Toast.makeText(AdActivity.this, "消失", Toast.LENGTH_LONG).show();
				
				AdActivity.this.startActivity(new Intent(AdActivity.this, MainActivity.class));
			}

		});
	}

}
