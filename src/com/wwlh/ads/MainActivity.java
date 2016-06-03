package com.wwlh.ads;
import com.wwlh.ads.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {
	RelativeLayout rlyt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rlyt = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.activiasdfty111_main, null);

		setContentView(rlyt);

		
		/*
		 *去掉注释，添加横幅广告 
		 */
		//AdView adView = new AdView(this, rlyt);
		final InterstitialAd  iad = new InterstitialAd(this);
		findViewById(R.id.btnInterAD).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				iad.show();
			}
		});

	}
}
