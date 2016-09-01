package com.bupt.dao;

public interface IWifiInfoDao {

	/**
	 * 向数据库插入信息wifi信息
	 */
	public void saveWifiInfoToDB();
	
	/**
	 * 
	 */
	public void saveHeartInfoToDB();
	
	/**
	 * 从数据库中查询信息
	 * 
	 */	
	public void getInfoInDB();

}
