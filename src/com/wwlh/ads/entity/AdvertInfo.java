package com.wwlh.ads.entity;

import android.text.format.Time;


public class AdvertInfo {

	protected ShowTimeRange	showTime		= new ShowTimeRange();
	protected String		advertKey		= null;
	protected int			advertid		= 0;
	protected String		imageUrl		= null;
	protected String		imageFilename	= null;
	protected int			imageType		= 0;
	protected int			type			= 0;
	protected Time			advertStartDate	= null;
	protected Time			advertEndDate	= null;
	protected int			interval;
	protected String		advertInfoUrl	= null;
	protected int			index			= 0;
	protected int			taskId			= 0;

	protected Time			clickDate		= null;
	protected Time			installDate		= null;
	protected Time			showDate		= null;
	protected Time			advertDate		= null;

	protected String		packageName		= null;
	protected int			replace			= 1;
	public ShowTimeRange getShowTime() {
		return showTime;
	}
	public void setShowTime(ShowTimeRange showTime) {
		this.showTime = showTime;
	}
	public String getAdvertKey() {
		return advertKey;
	}
	public void setAdvertKey(String advertKey) {
		this.advertKey = advertKey;
	}
	public int getAdvertid() {
		return advertid;
	}
	public void setAdvertid(int advertid) {
		this.advertid = advertid;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Time getAdvertStartDate() {
		return advertStartDate;
	}
	public void setAdvertStartDate(Time advertStartDate) {
		this.advertStartDate = advertStartDate;
	}
	public Time getAdvertEndDate() {
		return advertEndDate;
	}
	public void setAdvertEndDate(Time advertEndDate) {
		this.advertEndDate = advertEndDate;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public String getAdvertInfoUrl() {
		return advertInfoUrl;
	}
	public void setAdvertInfoUrl(String advertInfoUrl) {
		this.advertInfoUrl = advertInfoUrl;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public Time getClickDate() {
		return clickDate;
	}
	public void setClickDate(Time clickDate) {
		this.clickDate = clickDate;
	}
	public Time getInstallDate() {
		return installDate;
	}
	public void setInstallDate(Time installDate) {
		this.installDate = installDate;
	}
	public Time getShowDate() {
		return showDate;
	}
	public void setShowDate(Time showDate) {
		this.showDate = showDate;
	}
	public Time getAdvertDate() {
		return advertDate;
	}
	public void setAdvertDate(Time advertDate) {
		this.advertDate = advertDate;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public int getReplace() {
		return replace;
	}
	public void setReplace(int replace) {
		this.replace = replace;
	}
	
	
	
}
