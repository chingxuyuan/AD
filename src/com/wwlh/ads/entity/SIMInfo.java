
package com.wwlh.ads.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class SIMInfo {

	protected String	name	= null;
	protected boolean	ready	= false;

	public JSONObject toLocalJSON () throws JSONException {

		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("ready", ready);

		return json;

	}

	public void fromLocalJSON (JSONObject json) throws Exception {

		this.name = json.getString("name");
		this.ready = json.getBoolean("ready");
	}

	public String getName () {

		return name;
	}

	public void setName (String name) {

		this.name = name;
	}

	public boolean isReady () {

		return ready;
	}

	public void setReady (boolean ready) {

		this.ready = ready;
	}
}
