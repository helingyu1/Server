package com.bupt.minaserver;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.bupt.entity.AcessPoint;
import com.bupt.service.RequestService;
import com.bupt.utils.Helper;

/**
 * ������ղ����?ͬ���͵���Ϣ
 * ���ͽ�����byte[],ת��char[]������
 * @author helingyu
 *
 */
public class EchoSeverHandler extends IoHandlerAdapter {
	static int sum = 0;
	
	private final Logger logger = Logger.getLogger(EchoSeverHandler.class);
	// service
	RequestService service = new RequestService();

	// ���ƫ����
	private static final int MAC_OFFSET = 14;
	private static final int ACTION_OFFSET = 36;// outside��������
	private static final int PARA_OFFSET = 20;

	// ��־
	private static final int NO_SOCKET_ADDR = 4;
	private static final int NO_ERROR = 0;

	public static final CharsetDecoder decoder = (Charset.forName("UTF-8"))
			.newDecoder();

	/**
	 * MINA���쳣�ص�������
	 * <p>
	 * �����н����쳣����ʱ������close��ǰ�Ự��
	 * 
	 * @param session
	 *            �����쳣�ĻỰ
	 * @param cause
	 *            �쳣����
	 * @see IoSession#close(boolean)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// logger.error("[IMCORE]exceptionCaught���񵽴��ˣ�ԭ���ǣ�" +
		// cause.getMessage(),
		// cause);
		session.close(true);
	}

	/**
	 * MINA������յ��ͻ�����Ϣ�Ļص�������
	 * <p>
	 * ���ཫ�ڴ˷�����ʵ������ļ�ʱͨѶ��ݽ����ʹ�����ԡ�
	 * <p>
	 * Ϊ���������ܣ��������������ڶ�����MINA��IoProcessor֮����̳߳��У� ���
	 * {@link ServerLauncher#initAcceptor()}�е�MINA���ô��� ��
	 * 
	 * @param session
	 *            �յ���Ϣ��Ӧ�ĻỰ����
	 * @param message
	 *            �յ���MINA��ԭʼ��Ϣ��װ���󣬱������� {@link IoBuffer}����
	 * @throws Exception
	 *             ���д�����ʱ���׳��쳣
	 * 
	 *             ps:��������wifi socket����mobile����Ϣ
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// *********************************************** �������
		// step1:��ȡ�յ������
//		System.out.println(message);
		IoBuffer buffer = (IoBuffer) message;
		String hex = buffer.getHexDump();
		System.out.println("hex:"+hex);
		String[] recv = hex.split(" ");
		System.out.println(Arrays.toString(recv));
//		System.out.println("test :::::"+Arrays.toString(buffer.asCharBuffer().array()));;
//		System.out.println((String)message);
		// ���յ���byte����
		byte[] recv_b = buffer.array();
		
		// ת���ַ�����
//		char[] recv = Helper.getChars(recv_b);
//		logger.debug("���������յ�����ݣ�"+Arrays.toString(Helper.char2StringArray(recv)));
		logger.debug("���������յ�����ݣ�"+Arrays.toString(recv));

		// �õ�wifi_id
		StringBuffer sb = new StringBuffer();
		for (int i = MAC_OFFSET; i < MAC_OFFSET + 6; i++) {
//			sb.append(Helper.char2StringArray(recv)[i]);
			sb.append(recv[i]);
		}
		String mac_id = new String(sb);
		InetSocketAddress addr = (InetSocketAddress) session.getRemoteAddress();
//		AcessPoint ap = new AcessPoint(addr.getAddress().getHostAddress(),
//				addr.getPort(), mac_id,recv);
		String ipStr = addr.getAddress().getHostAddress();
		long ip = Helper.ipToLong(ipStr);
		AcessPoint ap = new AcessPoint(ip,
				addr.getPort(), mac_id,recv);
//		System.out.println(ap);

		// step2:�������
		int swt = Integer.parseInt(recv[0],16);
		logger.debug("swt:"+swt);
		if (swt == 0) { // ����1��д������Ϣ����ݿ�
			logger.debug("test:�����֧��1��");
			service.store_to_database(session,ap);
		} else if (swt == 99) { // ����2�����������Ƿ�����
			logger.debug("test:�����֧��2��");
			service.detect_alive(session,ap);
		} else if (swt > 100 && swt < 128) { // ����3�������Ϳ������������
			logger.debug("test:�����֧��3��");
			service.outside_send_to_socket(session,ap);
		} else if (swt >= 1 && swt < 128) { // ����4���鿴��������Ƿ�����
//			logger.debug("test:�����֧��4��");
			service.send_to_socket(session,ap);
		} else if (swt >= 128) { // ����5����ݰ�������ֱ�ӷ����ֻ�
			logger.debug("test:�����֧��5��");
			service.send_to_mobile(ap);
		}
	}
	

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
//		System.out.println(((InetSocketAddress)session.getRemoteAddress()).getHostName());
//		session.write("aaaa");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	public static void main(String[] args) {

//		String hex = "1111";
//		String[] arr = Helper.hexToStringArray(hex);
//		System.out.println(arr.length);
//		for (int i = 0; i < arr.length; i++)
//			System.out.println(arr[i]);
//		char[] aa = {0x63,0x63,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0x63};
//		String b = new String(aa);
//		System.out.println(b);
		System.out.println(Integer.parseInt("ff", 16));


	}
}
