package com.bupt.testclient;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.bupt.utils.Helper;

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
//			String toServer="6300000000000000000000000000000000000000";//服务器是否在线
//			byte[] soServerBytes = toServer.getBytes("UTF-8");
//			byte[] aa = {0x63,0x63,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			
//			byte[] aa = {0x63,0x63,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0x63};
			
			char[] aa = {0x63,0xe0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0x63};
			byte[] b = Helper.getBytes(aa);
//			System.out.println("客户端转后的byte数组："+Arrays.toString(b));
//			System.out.println("重新转成char数组："+Arrays.toString(Helper.getChars(b)));
// 开始发送
//			boolean ok = UDPUtils.send(soServerBytes, soServerBytes.length);
			boolean ok = UDPUtils.send(b, b.length);
			if (ok){
				System.out.println("发往服务端的信息已送出.");
			}
			else
				System.out.println("发往服务端的信息没有成功发出！！！");

			// 3000秒后进入下一次循环
			Thread.sleep(100000);
		}

	}

}
