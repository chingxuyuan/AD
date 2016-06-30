package com.wwlh.ads.activity;
import com.wwlh.ads.AdView;
import com.wwlh.ads.InterstitialAd;
import com.wwlh.ads.PushClient;
import com.wwlh.ads.R;
import com.wwlh.ads.R.id;
import com.wwlh.ads.R.layout;
import com.wwlh.ads.util.BitmapUtil;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {
	RelativeLayout rlyt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rlyt = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.activity_main, null);

		setContentView(rlyt);
		
		PushClient.instance(this);
		
		/*
		 *添加横幅广告 ，rlyt为父RelativeLayout布局，
		 */
		AdView.instance(this, rlyt);
		//AdView adView = new AdView(this, rlyt);
		
		
		/*
		 * 插屏广告，中间显示
		
		final InterstitialAd  iad = new InterstitialAd(this);
		findViewById(R.id.btnInterAD).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				iad.show();
			}
		});
		
		 */
		

	}
}
