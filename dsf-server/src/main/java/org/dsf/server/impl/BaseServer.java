package org.dsf.server.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.dsf.net.Connection;
import org.dsf.server.Notifier;
import org.dsf.server.Server;
import org.dsf.server.handler.EventHandler;


public class BaseServer implements Server, Runnable{

	private EventHandler 				eventHandler = null;
	private Selector 					selector = null;
	private ServerSocketChannel			sschannel = null;
	private InetSocketAddress			address = null;
	private TreeMap<Integer, Object> 	timers;
	protected Notifier 					notifier;
	
	
	private int 						port = 20382;
	private boolean 					isRuning = true;
	
	public void init() throws IOException {
		notifier = Notifier.getNotifier();
		
		selector = Selector.open();
		sschannel = ServerSocketChannel.open();
		sschannel.configureBlocking(false);
		address = new InetSocketAddress(port);
		ServerSocket ss = sschannel.socket();
		ss.bind(address);
		sschannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	/**
	 * @param isRuning the isRuning to set
	 */
	public void setRuning(boolean isRuning) {
		this.isRuning = isRuning;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	private Map<String ,Object> serviceEngine = new HashMap<String, Object>();

	public void stop() {
		this.setRuning(false);
	}

	public boolean isRunning() {
		return isRuning;
	}

	public void setHandler(EventHandler eventHandler) {
		// TODO Auto-generated method stub
		this.eventHandler = eventHandler;
	}
	
	public void run() {
		try {
			init();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int num = 0;
		while (true) {
			try {
				num = selector.select();
				if (num > 0) {
					Set selectedKeys = selector.selectedKeys();
					Iterator it = selectedKeys.iterator();
					while (it.hasNext()) {
						SelectionKey key = (SelectionKey)it.next();
						// 删除处理过的 SelectionKey
						it.remove();
						// 处理Accept事件
						if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
							handleAccept(key);
						}
						// 处理read事件
						if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
							handleRead(key);
						}
						
						// 处理write事件
						if (key.isValid() && (key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
							handleWrite(key);
						}
					}
				}
				
				// 现在需要处理超时的连接
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private void handleAccept(SelectionKey key) {
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		notifier.fireOnAccept();
		int rc = EventHandler.DEL;
		
		SocketChannel sc = null;
		try {
			sc = ssc.accept();
			sc.configureBlocking(false);
			Connection c = new Connection();
			c.setSocketChannel(sc);
			
			rc = eventHandler.handleAccept(c);	
			switch(rc) {
			case EventHandler.ADD:
				// 注册读操作,以进行下一步的读操作
		        sc.register(selector,  SelectionKey.OP_READ, c);
				break;
			case EventHandler.DEL:
				// 该Key是监听端口对应的key
				//key.cancel();
				break;
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void handleRead(SelectionKey key) {
		int rc = EventHandler.DEL;
		SocketChannel sc = (SocketChannel) key.channel();
		notifier.fireOnRead();
		rc = eventHandler.handleRead((Connection)key.attachment());
		switch(rc) {
		case EventHandler.ADD:
			// 注册读操作,以进行下一步的读操作
	        try {
				sc.register(selector,  SelectionKey.OP_READ, key.attachment());
			} catch (ClosedChannelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case EventHandler.DEL:
			key.cancel();
			break;
		}
	}
	
	private void handleWrite(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		notifier.fireOnWrite();
		eventHandler.handleWrite((Connection)key.attachment());
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
