package org.dsf.server.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
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
import org.dsf.server.EventHandler;


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

	
	public void start() {
		
	}

	public void stop() {
		this.setRuning(false);
	}

	public boolean isRunning() {
		return isRuning;
	}

	public void setHandler(EventHandler handler) {
		// TODO Auto-generated method stub
		
	}
	
	public void run() {
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
						if ((key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
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
		
		SocketChannel sc = null;
		try {
			sc = ssc.accept();
			sc.configureBlocking(false);
			Connection c = new Connection();
			c.setSc(sc);
			// 给key添加关联的对象，用于再次回调时直接取出使用
			key.attach(c);
			eventHandler.handleAccept(c);	
			
			// 注册读操作,以进行下一步的读操作
	        sc.register(selector,  SelectionKey.OP_READ, c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void handleRead(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		notifier.fireOnRead();
		eventHandler.handleRead((Connection)key.attachment());
	}
	
	private void handleWrite(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		notifier.fireOnWrite();
		eventHandler.handleWrite((Connection)key.attachment());
	}
}
