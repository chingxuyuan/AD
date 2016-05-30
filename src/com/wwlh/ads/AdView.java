package com.wwlh.ads;

import com.wwlh.ads.R;
import com.wwlh.ads.utils.WindowInfo;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AdView {

	private RelativeLayout container;

	private Context context;

	public AdView(Context context) {
		this.context = context;
		initContainer();
	}

	
	/**
	 * 广告容器，加载横幅广告
	 */
	private void initContainer() {
		container = new RelativeLayout(context);
		ImageView imgAd = new ImageView(context);
		imgAd.setScaleType(ImageView.ScaleType.FIT_XY);
		imgAd.setImageResource(R.drawable.t);
		int mp = RelativeLayout.LayoutParams.MATCH_PARENT;
		RelativeLayout.LayoutParams rllp = null;
		rllp = new RelativeLayout.LayoutParams(mp, mp);
		container.addView(imgAd, rllp);
	}
	
	
	private void loadImage(){
		
		
	}

	
	/**
	 * 将banner广告布局添加到父布局中
	 * @param parent 指定父布局，类型为RelativeLayout
	 */
	public void attachParent(RelativeLayout parent) {
		int width = RelativeLayout.LayoutParams.MATCH_PARENT;
		int height = WindowInfo.calcAdHeight(context);
		RelativeLayout.LayoutParams rllp = null;

		rllp = new RelativeLayout.LayoutParams(width, height);
		
		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		parent.addView(container, rllp);
	}

}
