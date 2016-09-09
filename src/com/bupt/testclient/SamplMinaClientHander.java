package com.bupt.testclient;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;  
import org.apache.mina.core.session.IdleStatus;  
import org.apache.mina.core.session.IoSession;  
  
/** 
 * ��Ϣ������ 
 * @author ���� 
 * 
 */  
public class SamplMinaClientHander extends IoHandlerAdapter {  
  
    @Override  
    public void exceptionCaught(IoSession arg0, Throwable arg1)  
            throws Exception {  
        // TODO Auto-generated method stub  
  
    }  
  
    /** 
     * ���ͻ��˽��ܵ���Ϣʱ 
     */  
    @Override  
    public void messageReceived(IoSession session, Object message) throws Exception {  
  
        //�������趨�˷���������Ϣ������һ��һ�ж�ȡ������Ϳ���תΪString:  
        String s = (String)message;  
          
        //Writer the received data back to remote peer  
        System.out.println("�������������յ���Ϣ: " + s);  
          
        //���Խ���Ϣ���͸�ͻ���  
        session.write(s);  
  
    }  
  
    @Override  
    public void messageSent(IoSession arg0, Object arg1) throws Exception {  
        // TODO Auto-generated method stub  
  
    }  
  
    /** 
     * ��һ���ͻ��˱��ر�ʱ 
     */  
    @Override  
    public void sessionClosed(IoSession session) throws Exception {  
        System.out.println("one client Disconnect");  
  
    }  
  
    @Override  
    public void sessionCreated(IoSession arg0) throws Exception {  
        // TODO Auto-generated method stub  
    	System.out.println(1111);
  
    }  
  
    @Override  
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {  
        // TODO Auto-generated method stub  
  
    }  
  
    /** 
     * ��һ���ͻ������ӽ���ʱ 
     */  
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
  
        System.out.println("incomming client:" + session.getRemoteAddress()); 
        byte[] aa = {0x64,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

        IoBuffer buffer = IoBuffer.wrap(aa);
        session.write(buffer);

    }  
  
}  