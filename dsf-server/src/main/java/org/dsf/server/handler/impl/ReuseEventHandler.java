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

import sun.nio.ch.DirectBuffer;

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
			int i = sc.read((ByteBuffer) buffer);
			byte[] bytes = (byte [])buffer.array();
			
			
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
	
	// 解析reuse包,只解析当前buffer中的内容
	private int parseReuse(Connection c, Buffer buffer) {
		ReuseContext reuseContext = (ReuseContext)c.getContext();
		byte[] bytes = (byte[]) buffer.array();
		
		// 包长度解析
		if (reuseContext.getState() < ReuseContext.STATE_DATA) {
			// parse datalen
			int index = byteFind(bytes, 0, buffer.position(), ReuseContext.CRLFCRLF);
			if (index > 0) {
			 	String dataLenStr = new String(bytes, 0, index);
			 	reuseContext.setDataLen(Integer.parseInt(dataLenStr));
			 	buffer.limit(buffer.position());
			 	buffer.position(index + ReuseContext.CRLFCRLF.length);
			 	
			} else {
				reuseContext.setState(ReuseContext.STATE_DATALEN);
			}
		}
		
		// 包体统计
		if (reuseContext.getState() == ReuseContext.STATE_DATA) {
			
		}
		
		return ReuseContext.STATE_DONE;
	}
	
	// 在byte中查找相应的关键字
	private int byteFind(byte[] bytes, int start, int end, byte[] flag) {
		int index = -1, i = 0, j = 0;
		if (bytes == null || flag == null 
				|| start < 0 || end < 0 || start >= end
				|| flag.length < 1 || bytes.length < flag.length) {
			return index;
		}
		for (i = start, j = 0; i < end - flag.length && j < flag.length; i++) {
			if (bytes[i] != flag[j]) {
				j = 0;
			} else {
				j++;
			}
		}
		if (j != flag.length) {
			index = -1;
		} else {
			index = i - flag.length;
		}
		return index;
	}
}
