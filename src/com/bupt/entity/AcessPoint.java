package com.bupt.entity;

import java.util.Arrays;

/**
 * 该实体类，代表与服务器通信的节点
 * @author helingyu
 *
 */

public class AcessPoint {
	private long ip;
	private int port;
	private String wifi_id;
	private String[] recv;
	public AcessPoint(long ip,int port,String wifi_id,String[] recv){
		this.ip = ip;
		this.port = port;
		this.wifi_id = wifi_id;
		this.recv = recv;
	}
	public long getIp() {
		return ip;
	}
	public void setIp(long ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String[] getRecv() {
		return recv;
	}
	public void setRecv(String[] recv) {
		this.recv = recv;
	}
	
	public String getWifi_id() {
		return wifi_id;
	}
	public void setWifi_id(String wifi_id) {
		this.wifi_id = wifi_id;
	}
	
	@Override
	public String toString() {
		return "AcessPoint [ip=" + ip + ", port=" + port + ", wifi_id="
				+ wifi_id + ", recv=" + Arrays.toString(recv) + "]";
	}
	
	

}
