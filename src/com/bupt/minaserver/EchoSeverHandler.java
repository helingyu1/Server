package com.bupt.minaserver;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class EchoSeverHandler extends IoHandlerAdapter {

	public static final CharsetDecoder decoder = (Charset.forName("UTF-8"))
			.newDecoder();

	/**
	 * MINA的异常回调方法。
	 * <p>
	 * 本类中将在异常发生时，立即close当前会话。
	 * 
	 * @param session
	 *            发生异常的会话
	 * @param cause
	 *            异常内容
	 * @see IoSession#close(boolean)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// logger.error("[IMCORE]exceptionCaught捕获到错了，原因是：" +
		// cause.getMessage(),
		// cause);
		session.close(true);
	}

	/**
	 * MINA框架中收到客户端消息的回调方法。
	 * <p>
	 * 本类将在此方法中实现完整的即时通讯数据交互和处理策略。
	 * <p>
	 * 为了提升并发性能，本方法将运行在独立于MINA的IoProcessor之外的线程池中， 详见
	 * {@link ServerLauncher#initAcceptor()}中的MINA设置代码 。
	 * 
	 * @param session
	 *            收到消息对应的会话引用
	 * @param message
	 *            收到的MINA的原始消息封装对象，本类中是 {@link IoBuffer}对象
	 * @throws Exception
	 *             当有错误发生时将抛出异常
	 * 
	 *             ps:接收来自wifi socket或者mobile的消息
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// *********************************************** 接收数据
		// step1:读取收到的数据
		IoBuffer buffer = (IoBuffer) message;
		// 收到的字节数组
		byte[] bytes = buffer.array();
		// step2:解析数据
		char swt = (char) bytes[0];
		if (swt == 0) {
			store_to_database();
		} else if (swt == 99) {
			detect_alive();
		} else if (swt > 100 && swt < 128) {
			outside_send_to_socket();
		} else if (swt >= 1 && swt < 128) {
			send_to_socket();
		} else if (swt >= 128) {
			send_to_mobile();
		}
		// String body = buffer.getString(decoder);
		// 注意：当客户使用不依赖于MINA库的情况下，以下官方推
		// 荐的读取方法会在数据首部出现几个字节的未知乱码
		// message.toString()
		// System.out.println("【NOTE】>>>>>> 收到客户端的数据：" + body);
		//
		// // *********************************************** 回复数据
		// String strToClient = "Hello，我是Server，我的时间戳是"
		// + System.currentTimeMillis();
		// byte[] res = strToClient.getBytes("UTF-8");
		// // 组织IoBuffer数据包的方法：本方法才可以正确地让客户端UDP收到byte数组
		// IoBuffer buf = IoBuffer.wrap(res);
		//
		// // 向客户端写数据
		// WriteFuture future = session.write(buf);
		// // 在100毫秒超时间内等待写完成
		// future.awaitUninterruptibly(100);
		// // The message has been written successfully
		// if (future.isWritten()) {
		// // send sucess!
		// }
		// // The messsage couldn't be written out completely for some reason.
		// // (e.g. Connection is closed)
		// else {
		// System.out.println("[IMCORE]回复给客户端的数据发送失败！");
		// }
	}

	/**
	 * 写入数据库(从收到的包中解析出wifi_id,wifi_ipv4,wifi_ipv4_port)
	 */
	public void store_to_database() {

	}

	/**
	 * 发往插座(从数据库中提取出wifi_ipv4,wifi_ipv4_port填充到数据包中)
	 */
	public void send_to_socket() {

	}

	/**
	 * 发送手机(数据包不作处理直接发往手机)
	 */
	public void send_to_mobile() {

	}

	public void detect_alive() {

	}

	/**
	 * 
	 */
	public void outside_send_to_socket() {

	}
}
