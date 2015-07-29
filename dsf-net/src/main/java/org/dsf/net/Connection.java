package org.dsf.net;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Connection {
	private SocketChannel 	socketChannel;
	private Object 			context;			// 连接对应的上下文		
	private Buffer 			readBuffer;
	private Buffer 			readExtraBuffer;
	private Buffer			writeBuffer;
	
	
	public Connection(int capacity) {
		readBuffer = ByteBuffer.allocate(capacity);
	}
	
	public Connection() {
		readBuffer = ByteBuffer.allocate(4096);
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel sc) {
		this.socketChannel = sc;
	}
	
	public Object getContext() {
		return context;
	}
	
	public void setContext(Object context) {
		this.context = context;
	}
	
	public void createReadBuffer(int capacity) {
		readBuffer = ByteBuffer.allocate(capacity);
	}
	
	public void createReadExtraBuffer(int capacity) {
		readExtraBuffer = ByteBuffer.allocate(capacity);
	}
	
	public void createWriteBuffer(int capacity) {
		writeBuffer = ByteBuffer.allocate(capacity);
	}
	
	public Buffer getReadExtraBuffer() {
		return readExtraBuffer;
	}
	
	public Buffer getReadBuffer() {
		return readBuffer;
	}
	
	public Buffer getWriteBuffer() {
		return writeBuffer;
	}
}
