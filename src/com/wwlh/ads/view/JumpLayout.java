package com.wwlh.ads.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JumpLayout extends RelativeLayout {

	private Context ctx;
	@SuppressLint("NewApi") public JumpLayout(Context context) {
		super(context);
		this.ctx = context;
		//设置背景色
		ImageView img = new ImageView(ctx);
		img.setBackgroundColor(Color.BLACK);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		img.setAlpha(0.1f);
		this.addView(img, params);
		
		TextView tv = new TextView(ctx);
		tv.setText("跳过");
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
		RelativeLayout.LayoutParams tp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv.setGravity(Gravity.CENTER);
		tp.addRule(RelativeLayout.CENTER_IN_PARENT);
		tv.setAlpha(0.8f);
		this.addView(tv, tp);
		
	}
	

}
