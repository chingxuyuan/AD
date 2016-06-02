package com.wwlh.ads.entity;

import java.util.Date;

public class AdvertInfo {

	protected int id = 0;
	protected String name = null;
	protected int type = 0;
	protected Date createTime = null;
	protected double price = 0.0;
	protected boolean enable = false;
	protected String packageName = null;
	protected String activeScript = null;
	protected boolean system = false;
	protected boolean replace = false;
	protected String resourceURL = null;
	protected String targetURL = null;
	protected int showCount = 0;
	protected int showInterval = 0;
	protected String memo = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getActiveScript() {
		return activeScript;
	}

	public void setActiveScript(String activeScript) {
		this.activeScript = activeScript;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

	public boolean isReplace() {
		return replace;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}

	public String getResourceURL() {
		return resourceURL;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getShowInterval() {
		return showInterval;
	}

	public void setShowInterval(int showInterval) {
		this.showInterval = showInterval;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
