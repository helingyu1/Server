package com.bupt.minaserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.ExpiringSessionRecycler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class MinaServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// ** Acceptor设置
		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
		// 此行代码能让你的程序整体性能提升10倍
		acceptor.getFilterChain().addLast("threadPool",
				new ExecutorFilter(Executors.newCachedThreadPool()));
		// 设置MINA2的IoHandler实现类
		acceptor.setHandler(new EchoSeverHandler());
		// 设置会话超时时间（单位：毫秒），不设置则默认是10秒，请按需设置
		acceptor.setSessionRecycler(new ExpiringSessionRecycler(60 * 1000));

		// ** UDP通信配置
		DatagramSessionConfig dcfg = acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);
		// 设置输入缓冲区的大小，压力测试表明：调整到2048后性能反而降低
		dcfg.setReceiveBufferSize(1024);
		// 设置输出缓冲区的大小，压力测试表明：调整到2048后性能反而降低
		dcfg.setSendBufferSize(1024);
		
		dcfg.setUseReadOperation(true);

		// ** UDP服务端开始侦听
		acceptor.bind(new InetSocketAddress(9999));
		System.out.println("开始监听");
	}

}
