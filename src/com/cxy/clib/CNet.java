/**
 * CNet.java
 * c
 * TODO
 * 2015-12-30
 */
package com.cxy.clib;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;

/**
 * CNet 目的：new一次，不用每次新建Response监听 <br/>
 * 
 * @author c <br/>
 *         TODO 对volldy框架进行再封装 <br/>
 *         2015-12-30 <br/>
 *         工作流程 <br/>
 *         1，传入上下文和handler，实例化Volley框架 <br/>
 *         2，调用request方法，传入url和请求码 <br/>
 *         3，每个请求生成一个CResponse监听，包含请求码 <br/>
 *         4，volley返回数据 <br/>
 *         5，CResponse把返回数据封装成Message，what = 请求码 <br/>
 *         6,通过handler把返回数据发送回去 <br/>
 *         7,handler回调函数，根据不同的message.what,调用不同的处理方法 <br/>
 *         8,over
 * 
 * 
 */
public class CNet extends CLib {

	private Handler handler;
	private RequestQueue mQueue;

	public CNet(Context context, Callback Callback) {
		super();
		mQueue = Volley.newRequestQueue(context);

		// 新建一个网络请求子线程的handler
		HandlerThread handlerThread = new HandlerThread("CNet"+System.currentTimeMillis());
		handlerThread.start();
		Looper looper = handlerThread.getLooper();
		this.handler = new Handler(looper, Callback);
	}

	/**
	 * 
	 * @param url
	 *            网络请求路径
	 * @param what
	 *            命令码，识别码，请求码。根据她区分每一个返回的数据
	 * 
	 */
	public void request(String url, int what) {
		CResponse response = new CResponse(what);
		Request request = new StringRequest(url, response.success,
				response.error);
		request.setShouldCache(false);
		mQueue.add(request);
	}

	/**
	 * 
	 * @param url
	 *            网络请求路径
	 * @param what
	 *            命令码，识别码，请求码。根据她区分每一个返回的数据
	 * 
	 */
	public void request(String url, final Map<String, String> params, int what) {
		CResponse response = new CResponse(what);
		Request<String> request = new StringRequest(Method.POST, url, response.success,
				response.error) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return params;
			}

		};

		request.setShouldCache(false);
		mQueue.add(request);
	}

	/**
	 * Volley 自定义请求返回的监听器
	 * 
	 * @author c
	 */
	class CResponse {

		private int msgWhat = -1;
		public Listener<String> success;
		public ErrorListener error;

		/**
		 * 实例化响应监听器
		 * 
		 * @param what
		 */
		public CResponse(int what) {
			super();
			this.msgWhat = what;

			// 返回成功的监听器，常用
			success = new Response.Listener<String>() {
				public void onResponse(String response) {
					Message msg = handler.obtainMessage();
					msg.what = msgWhat;
					msg.obj = response;
					msg.arg2 = 0;// 返回成功
					handler.sendMessage(msg);
				}
			};

			// 返回失败了，不喜欢用
			error = new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Message msg = handler.obtainMessage();
					msg.what = msgWhat;
					msg.arg2 = -1;// 返回失败
					msg.obj = error.getMessage();
					handler.sendMessage(msg);
				}
			};
		}
	}

}
