package org.dsf.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import org.dsf.common.DsfProviderBean;
import org.dsf.common.protocol.Invocation;
import org.dsf.net.Connection;
import org.dsf.server.handler.EventHandler;
import org.dsf.common.utils.*;

public class RpcServerHandler implements EventHandler {
	
	private Map<String ,Object> serviceEngine = new HashMap<String, Object>();
	
	public void register(DsfProviderBean dsfProviderBean) {
		// TODO Auto-generated method stub
		try {
			this.serviceEngine.put(dsfProviderBean.getServiceInterface(), dsfProviderBean.getTarget());
			System.out.println(serviceEngine);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public int handleWrite(Connection c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int handleRead(Connection c) {
		// TODO Auto-generated method stub
		System.out.println("handleRead");
		int rc = EventHandler.DEL;
		SocketChannel sc = c.getSocketChannel();
		Buffer buffer = c.getReadBuffer();
		
		try {
			int i = sc.read((ByteBuffer) buffer);
			System.out.println(i);
			if (buffer.position() == 472) {
				ByteArrayInputStream bi = new ByteArrayInputStream((byte[]) buffer.array());  
		        ObjectInputStream oi = new ObjectInputStream(bi);  
		        Object o = oi.readObject();
		        Invocation invo = (Invocation) o;
		        
		        
		        Object obj = serviceEngine.get(invo.getInterfaces().getName());
				if(obj!=null) {
					try {
						Method m = obj.getClass().getMethod(invo.getMethod().getMethodName(), invo.getMethod().getParams());
						Object result = m.invoke(obj, invo.getParams());
						invo.setResult(result);
					} catch (Throwable th) {
						th.printStackTrace();
					}
				} else {
					throw new IllegalArgumentException("has no these class");
				}
				System.out.println("over");
				rc = EventHandler.DEL;
				buffer.clear();
				System.out.println("pos: " + buffer.position());
				((ByteBuffer) buffer).put(ByteUtil.getByteBuffer(invo));
				buffer.limit(buffer.position());
				buffer.position(0);
				int ret = sc.write((ByteBuffer) buffer);
				//System.out.println("pos: " + pos + "ret " + ret);
			} else {
				rc = EventHandler.ADD;
			}
			
	        
			System.out.println(i);
			//buffer.position(i + buffer.position());
			System.out.println(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rc;
	}

	@Override
	public int handleAccept(Connection c) {
		// TODO Auto-generated method stub
		System.out.println("handleAccept");
		return handleRead(c);
	}

}
