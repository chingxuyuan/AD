package com.wwlh.ads.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BitmapUtil {

	public static Bitmap getCloseIcon(Context context) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				android.R.drawable.ic_menu_close_clear_cancel, options);
		int imageHeight = options.outHeight/10*8;
		int imageWidth = options.outWidth/10*8;
		
		Bitmap bb = Bitmap.createBitmap(imageWidth, imageHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(bb);
		Paint p = new Paint();
		p.setColor(Color.GRAY);// 设置红色
		p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		int radius = imageHeight / 2;
		canvas.drawCircle(radius, radius, radius, p);// 大圆
		canvas.save();
		Paint pt = new Paint();
		pt.setStrokeWidth(imageHeight/15);   
		pt.setColor(Color.WHITE);// 设置红色
		pt.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		
		int start = imageWidth/10*3;
		
		int stop = imageWidth-start;
		// 画一条斜线 \
		canvas.drawLine(start, start, stop, stop, pt);
		canvas.save();
		
		// 再画一条斜线 /
		canvas.drawLine(stop,start, start, stop, pt);
		canvas.restore();
		return bb;
	}

}
