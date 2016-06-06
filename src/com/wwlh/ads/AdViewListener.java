package com.wwlh.ads;
import com.wwlh.ads.interfaces.IAdListener;
public interface AdViewListener extends  IAdListener{
	
	/**
	 * 广告切换中
	 */
	//public void onAdSwitch();

	/**
	 *  广告已经渲染出来
	 * @param info
	 */
	//public void onAdShow(JSONObject info);

	/**
	 *  资源已经缓存完毕，还没有渲染出来
	 * @param adView
	 */
	//public void onAdReady(AdView adView);

	/**
	 * 广告已经渲染出来
	 * @param reason
	 */
	//public void onAdFailed(String reason);
	
}
