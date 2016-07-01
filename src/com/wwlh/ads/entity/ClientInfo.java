package com.wwlh.ads.entity;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wwlh.ads.util.DateTimeTool;


import android.text.format.Time;

public class ClientInfo {

	protected String				ip				= null;
	protected String				model			= null;
	protected String				netId			= null;
	protected String				osVersion		= null;
	protected String				IMEI			= null;
	protected ArrayList<SIMInfo>	sims			= null;
	protected String				MAC				= null;
	protected String				os				= null;
	protected String				provider		= null;
	protected String				brand			= null;
	protected String				secretKey		= null;
	protected String				releaseKey		= null;
	protected String				IMSI			= null;
	protected boolean				system			= false;
	protected Time					lastPostTime	= null;
	protected Time					lastShowTime	= null;

	public JSONObject toJSON () throws Exception {

		JSONObject json = new JSONObject();

		json.put("phone_ip", ip);
		json.put("model", model);
		json.put("net_id", netId);
		json.put("os_version", osVersion);
		json.put("phone_imei", IMEI);
		json.put("to_sim0", sims.isEmpty() ? 0 : 1);
		json.put("phone_mac", MAC);
		json.put("release_key", releaseKey);
		json.put("phone_os", os);
		json.put("sim_provider", provider);
		json.put("brand", brand);
		json.put("secret_key", secretKey);
		json.put("phone_imsi", IMSI);
		json.put("is_system", system ? 1 : 0);

		return json;
	}

	public JSONObject toLocalJSON () throws Exception {

		JSONObject json = new JSONObject();

		json.put("lastPostTime", DateTimeTool.timeToString(lastPostTime));
		json.put("lastShowTime", DateTimeTool.timeToString(lastShowTime));

		return json;
	}

	public void fromLocalJSON (JSONObject json) throws Exception {

		if (json == null) {
			throw new NullPointerException();
		}

		lastPostTime = DateTimeTool.parse(json.getString("lastPostTime"));
		lastShowTime = DateTimeTool.parse(json.getString("lastShowTime"));
	}

	protected JSONArray toJSONSIMs (ArrayList<SIMInfo> sims) throws JSONException {

		JSONArray json = new JSONArray();

		for (SIMInfo sim : sims) {

			json.put(sim.toLocalJSON());

		}

		return json;

	}

	protected void fromJSONSIMs (JSONArray json, ArrayList<SIMInfo> sims) throws Exception {

		sims.clear();

		if (json != null) {
			for (int index = 0; index < json.length(); index++) {

				JSONObject item = json.getJSONObject(index);
				SIMInfo sim = new SIMInfo();
				sim.fromLocalJSON(item);
				sims.add(sim);
			}
		}
	}

	public ClientInfo() {

		sims = new ArrayList<SIMInfo>();
	}

	public String getIp () {

		return ip;
	}

	public void setIp (String ip) {

		this.ip = ip;
	}

	public String getModel () {

		return model;
	}

	public void setModel (String model) {

		this.model = model;
	}

	public String getNetId () {

		return netId;
	}

	public void setNetId (String netId) {

		this.netId = netId;
	}

	public String getOsVersion () {

		return osVersion;
	}

	public void setOsVersion (String osVersion) {

		this.osVersion = osVersion;
	}

	public String getIMEI () {

		return IMEI;
	}

	public void setIMEI (String iMEI) {

		IMEI = iMEI;
	}

	public ArrayList<SIMInfo> getSims () {

		return sims;
	}

	public String getMAC () {

		return MAC;
	}

	public void setMAC (String mAC) {

		MAC = mAC;
	}

	public String getOs () {

		return os;
	}

	public void setOs (String os) {

		this.os = os;
	}

	public String getProvider () {

		return provider;
	}

	public void setProvider (String provider) {

		this.provider = provider;
	}

	public String getBrand () {

		return brand;
	}

	public void setBrand (String brand) {

		this.brand = brand;
	}

	public String getSecretKey () {

		return secretKey;
	}

	public void setSecretKey (String secretKey) {

		this.secretKey = secretKey;
	}

	public String getReleaseKey () {

		return releaseKey;
	}

	public void setReleaseKey (String releaseKey) {

		this.releaseKey = releaseKey;
	}

	public String getIMSI () {

		return IMSI;
	}

	public void setIMSI (String iMSI) {

		IMSI = iMSI;
	}

	public boolean isSystem () {

		return system;
	}

	public void setSystem (boolean system) {

		this.system = system;
	}

	public Time getLastPostTime () {

		return lastPostTime;
	}

	public void setLastPostTime (Time lastPostTime) {

		this.lastPostTime = lastPostTime;
	}

	public String getSIMName (int index) {

		if (index < 0 || index >= sims.size()) {
			return null;
		} else {
			return sims.get(index).getName();
		}
	}

	public boolean hasSIMInstalled (int index) {

		if (index < 0 || index >= sims.size()) {
			return false;
		} else {
			return sims.get(index).isReady();
		}
	}

	public Time getLastShowTime () {

		return lastShowTime;
	}

	public void setLastShowTime (Time lastShowTime) {

		this.lastShowTime = lastShowTime;
	}

	public void setSims (ArrayList<SIMInfo> sims2) {

	}

	public void clear () {

		this.lastShowTime = null;
	}
}
