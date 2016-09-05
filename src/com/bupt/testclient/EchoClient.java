package com.bupt.testclient;

import java.io.UnsupportedEncodingException;

public class EchoClient {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		// TODO Auto-generated method stub
		// 初始化本地UDP的Socket
		LocalUDPSocketProvider.getInstance().initSocket();
		// 启动本地UDP监听（接收数据用的）
		LocalUDPDataReciever.getInstance().startup();

		// 循环发送数据给服务端
		while (true) {
			// 要发送的数据
//			String toServer = "Hi，我是客户端，我的时间戳" + System.currentTimeMillis();
//			String toServer="6D000000000000000000000000001afe34f754e10000000000000000000000000000000000";
			String toServer="6300000000000000000000000000000000000000";//服务器是否在线
//			byte[] soServerBytes = toServer.getBytes("UTF-8");
			byte[] aa = {0x63,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			// 开始发送
//			boolean ok = UDPUtils.send(soServerBytes, soServerBytes.length);
			boolean ok = UDPUtils.send(aa, aa.length);
			if (ok){
				System.out.println("发往服务端的信息已送出.");
				Thread.sleep(5000);
				UDPUtils.send(aa, aa.length);
				System.out.println("再一次发出");
			}
			else
				System.out.println("发往服务端的信息没有成功发出！！！");

			// 3000秒后进入下一次循环
			Thread.sleep(100000);
		}

	}

}
