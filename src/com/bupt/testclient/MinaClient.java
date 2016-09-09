package com.bupt.testclient;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.DatagramConnector;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
  
/** 
 * 启动客户端 
 * @author 
 * 
 */  
public class MinaClient {  
  
    public static void main(String []args)throws Exception{  
          
        //Create TCP/IP connection  
        //NioSocketConnector connector = new NioSocketConnector();  
        NioDatagramConnector connector = new NioDatagramConnector();
          
        //创建接受数据的过滤器  
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
          
        //设定这个过滤器将一行一行(/r/n)的读取数据  
//        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));  
          
        //服务器的消息处理器：一个SamplMinaServerHander对象  
        connector.setHandler(new SamplMinaClientHander());  
          
        //set connect timeout  
        connector.setConnectTimeout(30);  
        //连接到服务器：  
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",12345));  
          
        //Wait for the connection attempt to be finished.  
        cf.awaitUninterruptibly();  
          
        cf.getSession().getCloseFuture().awaitUninterruptibly(); 
        
        IoSession session = cf.getSession();
        byte[] aa = {0x63,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        session.write(aa);
          
        connector.dispose();  
    }  
      
}  