package com.bupt.entity;
/**
 * 该实体对应Record表中记录
 * @author helingyu
 *
 */

public class Record {

	private String wifi_id;
	private long wifi_ipv4;
	private int wifi_ipv4_port;
	private int wifi_ipv6;
	private int wifi_ipv6_port;
	private int time;
	private boolean isRecorded;
	public String getWifi_id() {
		return wifi_id;
	}
	public void setWifi_id(String wifi_id) {
		this.wifi_id = wifi_id;
	}
	public long getWifi_ipv4() {
		return wifi_ipv4;
	}
	public void setWifi_ipv4(long wifi_ipv4) {
		this.wifi_ipv4 = wifi_ipv4;
	}
	public int getWifi_ipv4_port() {
		return wifi_ipv4_port;
	}
	public void setWifi_ipv4_port(int wifi_ipv4_port) {
		this.wifi_ipv4_port = wifi_ipv4_port;
	}
	public int getWifi_ipv6() {
		return wifi_ipv6;
	}
	public void setWifi_ipv6(int wifi_ipv6) {
		this.wifi_ipv6 = wifi_ipv6;
	}
	public int getWifi_ipv6_port() {
		return wifi_ipv6_port;
	}
	public void setWifi_ipv6_port(int wifi_ipv6_port) {
		this.wifi_ipv6_port = wifi_ipv6_port;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isRecorded() {
		return isRecorded;
	}
	public void setRecorded(boolean isRecorded) {
		this.isRecorded = isRecorded;
	}
	

}
