package com.wwlh.ads.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;

import com.wwlh.ads.view.Rotate3dAnimation;


public class AnimationUtil {

	public static  Rotate3dAnimation  getRolate360(){
		
		  // 获取布局的中心点位置，作为旋转的中心点  
        float centerX = WindowInfo.screenWidth / 2f;  
        
        float centery = WindowInfo.screenWidth/6 / 2f;  
        // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的  
        final Rotate3dAnimation rotation = new Rotate3dAnimation(-90, 90, centerX,  
        		centery, 100.0f, false);  
        // 动画持续时间500毫秒  
        rotation.setDuration(5000);  
		
        return rotation;
		
	}
	
	public static  AlphaAnimation  getAlphaInvisable(){
		
		AlphaAnimation alpha = new AlphaAnimation(1,0.5f);
		
		alpha.setDuration(1000);
		
		return alpha;
	}
}
