package com.wwlh.ads.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

public class TimerImage extends ImageView {

	public TimerImage(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GRAY);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);// 设置红色
		paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		canvas.drawText("1", 0, 0, paint);
		
		super.onDraw(canvas);
	}

	
}
