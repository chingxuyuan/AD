package com.wwlh.ads;

import com.google.gson.JsonObject;
import com.wwlh.ads.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	RelativeLayout rlyt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rlyt = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.activiasdfty111_main, null);

		setContentView(rlyt);

		AdView adView = new AdView(this, rlyt);
		
		adView.setIntervals(500);
		
		adView.setAdViewListener(new AdViewListener() {

			@Override
			public void onAdClick(JsonObject info) {
				
			}
		});

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

				}
			}, 3000);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
