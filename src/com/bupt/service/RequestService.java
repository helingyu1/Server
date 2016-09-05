package com.bupt.service;

import java.util.Arrays;
import java.util.Calendar;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.bupt.entity.AcessPoint;
import com.bupt.entity.Record;
import com.bupt.utils.Helper;

/**
 * 用来处理不同请求的service层
 * 
 * @author helingyu
 * 
 */
public class RequestService {
	private DaoService service = new DaoService();

	// 数据偏移量
	private static final int MAC_OFFSET = 14; 	//mac地址起始地址
	private static final int ACTION_OFFSET = 36;// outside方法里用，动作参数起始地址（1字节），0是关，1是开
	private static final int PARA_OFFSET = 20;	//设备密码起始地址，16字节（目前都是0？）

	// 标志
	private static final int NO_SOCKET_ADDR = 4;
	private static final int NO_ERROR = 0;

	/**
	 * 写入数据库(从收到的包中解析出wifi_id,wifi_ipv4,wifi_ipv4_port) 写两张表 record 和
	 * heartnumber
	 */
	public void store_to_database(IoSession session, AcessPoint ap) {

		Record record = new Record();
		record.setWifi_id(ap.getWifi_id());
		record.setWifi_ipv4(Integer.parseInt(ap.getIp()));
		record.setWifi_ipv4_port(ap.getPort());
		// step1:写heartnumber表
		service.writeToHeartNumber(ap.getWifi_id());
		// step2:写record表
		service.writeToRecord(record);
		// step3:生成响应，返回给ap
		WriteFuture future = session.write("aaaa");

	}

	/**
	 * 发往插座(从数据库中提取出wifi_ipv4,wifi_ipv4_port填充到数据包中)
	 */
	public void send_to_socket(AcessPoint ap) {

		// step1:在record表中查询wifi_ipv4,wifi_ipv4_port

		// step2:根据查出ip 端口号，向其发送信息，测试是否在线

		// step3:向手机发送响应信息

	}

	/**
	 * 发送手机(数据包不作处理直接发往手机)
	 */
	public void send_to_mobile(AcessPoint ap) {
		// 收到消息后，直接原封不动原路径返回
	}

	/**
	 * 检测本服务器是否在线
	 */
	public static void detect_alive(AcessPoint ap) {
		String[] rtn = new String[20];
		for(int i=0;i<rtn.length;i++)
			rtn[i] = "00";
		// 收到请求，添加时间信息，返回
		// 获取当前时间信息
		Calendar cal = Calendar.getInstance();
		String year = Helper.fill(Integer.toHexString(cal.get(Calendar.YEAR)),4,'0');
		String month = Helper.fill(Integer.toHexString(cal.get(Calendar.MONTH)+1),2,'0');
		String day = Helper.fill(Integer.toHexString(cal.get(Calendar.DATE)),2,'0');
		String hour = Helper.fill(Integer.toHexString(cal.get(Calendar.HOUR_OF_DAY)),2,'0');
		String min = Helper.fill(Integer.toHexString(cal.get(Calendar.MINUTE)),2,'0');
		String sec = Helper.fill(Integer.toHexString(cal.get(Calendar.SECOND)),2,'0');
		rtn[6] = year.substring(2, 4);//年份低字节
		rtn[7] = year.substring(0,2);//年份高字节
		rtn[8] = month;
		rtn[9] = day;
		rtn[10] = hour;
		rtn[11] = min;
		rtn[12] = sec;
		System.out.println(Arrays.toString(rtn));
		// 若收到的消息 recv_buf[18]==0x5f && recv_buf[19]==0x3f :heartbeat reply
		// success!
	}

	/**
	 * 
	 */
	public void outside_send_to_socket(AcessPoint ap) {
		StringBuffer sb = new StringBuffer();
		for (int i = MAC_OFFSET; i < MAC_OFFSET + 6; i++) {
			sb.append(ap.getRecv()[i]);
		}
		String mac_id = new String(sb);
		System.out.println("mac_id is:" + mac_id);
		// 跟sent_to_socket差不多
	}
	public static void main(String[] args){
		detect_alive(null);
	}
}
