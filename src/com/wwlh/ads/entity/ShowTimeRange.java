
package com.wwlh.ads.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.wwlh.ads.util.DateTimeTool;

import android.text.format.Time;

public class ShowTimeRange {

	protected Time	startTime	= null;
	protected Time	endTime		= null;

	public ShowTimeRange() {

		super();
	}

	public JSONObject toLocalJSON () throws JSONException {

		JSONObject json = new JSONObject();

		json.put("startTime", DateTimeTool.timeToString(startTime));
		json.put("endTime", DateTimeTool.timeToString(endTime));

		return json;

	}

	public void fromLocalJSON (JSONObject json) throws Exception {

		startTime = DateTimeTool.parse(json.getString("startTime"));
		endTime = DateTimeTool.parse(json.getString("endTime"));
	}

	public void fromValuePair (String s) {

		if (s == null) {
			startTime = null;
			endTime = null;
		} else {
			String[] ss = s.split("-");

			if (ss.length != 2) {
				startTime = null;
				endTime = null;
			} else {
				startTime = DateTimeTool.timeForHour(Integer.valueOf(ss[0]));
				endTime = DateTimeTool.timeForHour(Integer.valueOf(ss[1]));
			}
		}
	}

	public Time getStartTime () {

		return startTime;
	}

	public void setStartTime (Time startTime) {

		this.startTime = startTime;
	}

	public Time getEndTime () {

		return endTime;
	}

	public void setEndTime (Time endTime) {

		this.endTime = endTime;
	}

	public boolean isAtTime (Time time) {

		return DateTimeTool.isInTimeRangeOfDay(startTime, endTime, time);
	}
}
