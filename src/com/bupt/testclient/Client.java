package com.bupt.testclient;

/**
 * @author LiuJiazhi
 * @version 1.0
 * @created 16/9/9
 */
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramConnector;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class Client extends IoHandlerAdapter {
  //  private static Logger logger = Logger.getLogger(Client.class);

    DatagramConnector connector;

    IoSession session;

    public Client() {
        connector = new NioDatagramConnector();
        connector.setHandler(this);
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("logger", new LoggingFilter());
        chain.addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.NUL, LineDelimiter.NUL)));
        connector.setHandler(new SamplMinaClientHander());
        IoFuture connFuture = connector.connect(new InetSocketAddress(
                "192.168.0.8", 9999));
        connFuture.addListener(new IoFutureListener() {
            public void operationComplete(IoFuture future) {
                ConnectFuture connFuture = (ConnectFuture) future;
                if (connFuture.isConnected()) {
                    session = future.getSession();
                    try {
                        sendData();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                   // logger.error("Not connected...exiting");
                }
            }
        });
    }

    private void sendData() throws InterruptedException {
        session.write("测试数据发送！");
    }

    public static void main(String[] args) {
        new Client();
    }
}