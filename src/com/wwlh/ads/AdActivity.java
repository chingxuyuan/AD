package com.wwlh.ads;

import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		SplashAd splashAd = new SplashAd(this);
		splashAd.setSplashAdListener(new SplashAdListener() {
			
			@Override
			public void onAdClick(JsonObject info) {
				Toast.makeText(AdActivity.this, "dianji", Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onDismissed() {
				Toast.makeText(AdActivity.this, "消失", Toast.LENGTH_LONG).show();
				
				AdActivity.this.startActivity(new Intent(AdActivity.this, MainActivity.class));
			}
		});
	}

}
