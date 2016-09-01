package com.bupt.service;

import com.bupt.entity.Record;

public class WifiOptService {

	/**
	 * 写入record表
	 */
	public void writeToRecord(Record record){
		
	}
	/**
	 * 写入heartnumber表
	 */
	public void writeToHeartNumber(String wifi_id){
		//step1:现在heartdevice中查询是否存在该id的记录，没有的话返回
		//step2：如果有，判断heartnumber表中是否有该id记录，没有的话3，有的话4
		//step3:插入一条新记录(heartnum字段设为1)
		//step4：将heartnum字段值+1
	}
}
