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
 * �����ͻ��� 
 * @author 
 * 
 */  
public class MinaClient {  
  
    public static void main(String []args)throws Exception{  
          
        //Create TCP/IP connection  
        //NioSocketConnector connector = new NioSocketConnector();  
        NioDatagramConnector connector = new NioDatagramConnector();
          
        //����������ݵĹ�����  
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
          
        //�趨�����������һ��һ��(/r/n)�Ķ�ȡ���  
//        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));  
          
        //����������Ϣ��������һ��SamplMinaServerHander����  
        connector.setHandler(new SamplMinaClientHander());  
          
        //set connect timeout  
        connector.setConnectTimeout(30);  
        //���ӵ���������  
        ConnectFuture cf = connector.connect(new InetSocketAddress("192.168.0.8",9999));
          
        //Wait for the connection attempt to be finished.  
        cf.awaitUninterruptibly();  
          
        cf.getSession().getCloseFuture().awaitUninterruptibly(); 
        
        IoSession session = cf.getSession();
        byte[] aa = {0x22,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        session.write(aa);
          
        connector.dispose();  
    }  
      
}  