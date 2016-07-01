package com.wwlh.ads;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.wwlh.ads.entity.ClientInfo;
import com.wwlh.ads.entity.PushMessage;
import com.wwlh.ads.util.ClientInfoFetcher;

import android.content.Context;
import android.util.Log;


/**
 * 推送客户端
 * @author c
 *
 */
public class PushClient {
	private String TAG = "PushClient";
	//应用程序上下文
	private Context appContext;
	//推送服务器地址
	private String serverURI = "tcp://112.124.127.154:1883";
	//该客户端ID，IMEI
	private String clientId = "t";
	//订阅主题，推送服务器根据主题推送到不同的客户端
	private String subscriptionTopic = "wwlh";
	//MQTT客户端
	private static MqttAndroidClient mqttAndroidClient;

	public PushClient(Context ctx) {
		super();
		appContext = ctx.getApplicationContext();
		init();
	}
	
	public static PushClient instance(Context ctx){
		return new PushClient(ctx);
	}

	
	/**
	 * 配置初始化推送客户端
	 */
	public void init() {
		
		ClientInfo client = new ClientInfo();
		ClientInfoFetcher.fetchBaseInfo(appContext, client);
		clientId = client.getIMEI();
		Log.i(TAG, clientId);

		if(mqttAndroidClient!=null){
			if (mqttAndroidClient.isConnected()) {
				Log.i("PushClient", "isConnected");
				return;
			}
		}
		mqttAndroidClient = new MqttAndroidClient(appContext, serverURI,
				clientId);
		
		mqttAndroidClient.setCallback(mqttCallback);

		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		  //设置超时时间  
		mqttConnectOptions.setConnectionTimeout(10000);  
          
        //设置会话心跳时间  
		mqttConnectOptions.setKeepAliveInterval(50000);  
		
		 //设置是否清除会话信息  
		mqttConnectOptions.setCleanSession(false);
		try {
			mqttAndroidClient
					.connect(mqttConnectOptions, null, connectListener);
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 连接服务器监听，成功后订阅主题
	 */
	private IMqttActionListener connectListener = new IMqttActionListener() {

		@Override
		public void onFailure(IMqttToken arg0, Throwable arg1) {

		}

		@Override
		public void onSuccess(IMqttToken arg0) {
			subscribeToTopic();
			Log.i("PushClient", "onSuccess");
		}

	};

	/**
	 * 订阅主题，可接受后台发布的相应主题的信息
	 */
	private void subscribeToTopic() {
		try {
			mqttAndroidClient.subscribe(subscriptionTopic, 2, null,
					subscribeListener);
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 订阅监听器
	 */
	private IMqttActionListener subscribeListener = new IMqttActionListener() {

		@Override
		public void onFailure(IMqttToken arg0, Throwable arg1) {

		}

		@Override
		public void onSuccess(IMqttToken arg0) {
		}

	};

	
	
	/**
	 * MQTT回调函数，异步获取推送信息
	 */
	private MqttCallback mqttCallback = new MqttCallback() {

		@Override
		public void connectionLost(Throwable arg0) {

		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		//推送消息到达，等待处理
		@Override
		public void messageArrived(String arg0, MqttMessage msg)
				throws Exception {
			Log.i("PushClient", "messageArrived: "+new String(msg.getPayload()));
			pushNotification(msg);
		}

	};
	
	private void pushNotification(MqttMessage msg) {
		String json = new String(msg.getPayload());
		Gson gson = new Gson();
		PushMessage push = gson.fromJson(json, PushMessage.class);
		PushNotification pushNotification = new PushNotification(appContext);
		pushNotification.notify(push);
		 
	}

}
