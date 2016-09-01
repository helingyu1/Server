package com.bupt.dao;

public interface IWifiInfoDao {

	/**
	 * 向数据库插入信息wifi信息
	 */
	public void saveInfoToRecord();
	
	/**
	 * 
	 */
	public void saveHeartInfoToDB();
	
	/**
	 * 从数据库中查询信息
	 * 
	 */	
	public void getInfoFromRecord(String wifi_id);

}
