package com.bupt.service;

import com.bupt.dao.DBDaoImpl;
import com.bupt.entity.Record;

public class DaoService {

	/**
	 * 写入record表
	 */
	public void writeToRecord(Record record) {
		//使用replace into（有则更新，无则添加）
		DBDaoImpl.replaceToRecord(record);
	}

	/**
	 * 写入heartnumber表
	 */
	public void writeToHeartNumber(String wifi_id) {

		// step1:现在heartdevice中查询是否存在该id的记录
		// 没有的话返回,如果有跳到step2
		if (!DBDaoImpl.hasItemInHeartdevice(wifi_id))
			return;

		// step2：判断heartnumber表中是否有该id记录
		// 没有的话跳到3，有的话4
		if (!DBDaoImpl.hasItemInHeartnumber(wifi_id)) {
			// step3:插入一条新记录(heartnum字段设为1)
			DBDaoImpl.insertToHeartnumber(wifi_id);
		} else {
			// step4：将heartnum字段值+1
			DBDaoImpl.updateInHeartnumber(wifi_id);
		}
	}
}
