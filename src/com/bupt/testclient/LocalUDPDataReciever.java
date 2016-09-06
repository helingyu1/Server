package com.bupt.testclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import com.bupt.utils.Helper;

public class LocalUDPDataReciever {

	private static final String TAG = LocalUDPDataReciever.class
			.getSimpleName();
	private static LocalUDPDataReciever instance = null;
	private Thread thread = null;

	public static LocalUDPDataReciever getInstance() {
		if (instance == null)
			instance = new LocalUDPDataReciever();
		return instance;
	}

	public void startup() {
		this.thread = new Thread(new Runnable() {
			public void run() {
				try {
//					Log.d(LocalUDPDataReciever.TAG, "本地UDP端口侦听中，端口="
//							+ ConfigEntity.localUDPPort + "...");

					// 开始侦听
					LocalUDPDataReciever.this.udpListeningImpl();
				} catch (Exception eee) {
//					Log.w(LocalUDPDataReciever.TAG, "本地UDP监听停止了(socket被关闭了?),"
//							+ eee.getMessage(), eee);
				}
			}
		});
		this.thread.start();
	}

	private void udpListeningImpl() throws Exception {
		while (true) {
			byte[] data = new byte[1024];
			// 接收数据报的包
			DatagramPacket packet = new DatagramPacket(data, data.length);

			DatagramSocket localUDPSocket = LocalUDPSocketProvider
					.getInstance().getLocalUDPSocket();
			if ((localUDPSocket == null) || (localUDPSocket.isClosed()))
				continue;

			// 阻塞直到收到数据
			localUDPSocket.receive(packet);

			// 解析服务端发过来的数据
//			String pFromServer = new String(packet.getData(), 0,
//					packet.getLength(), "UTF-8");
//			System.out.println("【NOTE】>>>>>> 收到服务端的消息："
//					+ pFromServer);
			System.out.println("【NOTE】>>>>>> 收到服务端的消息："+Arrays.toString(Helper.char2StringArray(Helper.getChars(packet.getData()))));
			char[] aa = {0x63,0xe0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0x63};
			byte[] b = Helper.getBytes(aa);
			boolean ok = UDPUtils.send(b, b.length);
			if (ok){
				System.out.println("响应信息已送出.");
			}
			else
				System.out.println("发往服务端的信息没有成功发出！！！");
		}
	}

}
