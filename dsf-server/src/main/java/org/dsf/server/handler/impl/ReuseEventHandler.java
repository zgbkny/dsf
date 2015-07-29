package org.dsf.server.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.dsf.net.Connection;
import org.dsf.net.ReuseContext;
import org.dsf.server.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 
 * 处理TCP复用的逻辑, 主要功能是接收数据，并将一个包完整的接收下来，然后解析后传递给下一个Handler
 * @author william.ww
 *
 */
public class ReuseEventHandler implements EventHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ReuseEventHandler.class);
	
	@Override
	public int handleWrite(Connection c) {
		// TODO Auto-generated method stub
		log.info("handleWrite");
		
		return 0;
	}

	@Override
	public int handleRead(Connection c) {
		// TODO Auto-generated method stub
		log.info("handleRead");
		int rc = EventHandler.DEL;
		SocketChannel 	sc = c.getSocketChannel();
		Buffer 			buffer = c.getReadBuffer();
		ReuseContext 	reuseContext = getReuseContext(c);
		if (reuseContext == null) {
			// error
			return rc;
		}
		try {
			System.out.println(buffer.position());
			int i = sc.read((ByteBuffer) buffer);
			System.out.println(i);
			byte[] bytes = (byte [])buffer.array();
			System.out.println(buffer.position());
			String str = new String(bytes);
			System.out.println(str.length());
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int handleAccept(Connection c) {
		// TODO Auto-generated method stub
		log.info("handleAccept");
		return handleRead(c);
	}
	
	private ReuseContext getReuseContext(Connection c) {
		ReuseContext 	reuseContext = (ReuseContext) c.getContext();
		if (reuseContext == null) {
			reuseContext = new ReuseContext();
			c.setContext(reuseContext);
		}
		return reuseContext;
	}
}
