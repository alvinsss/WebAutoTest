package com.fengjr.util;

public class BindInfo {
	private String currInfo;
	private long lastAccessTime;
	private String randomCode;
	public String getCurrInfo() {
		return currInfo;
	}
	public void setCurrInfo(String currInfo) {
		this.currInfo = currInfo;
	}
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(long l) {
		this.lastAccessTime = l;
	}
	public String getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

}
