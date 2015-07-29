package org.dsf.client.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

import org.dsf.client.Client;
import org.dsf.common.DsfConsumerBean;
import org.dsf.common.RPC;
import org.dsf.common.protocol.Invocation;

public class BaseClient implements Client {
	private String host;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public BaseClient() {
		
	}
	
	public BaseClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;    
	}


	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}


	public void init() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		oos = new ObjectOutputStream(socket.getOutputStream());
	}

	public void invoke(Invocation invo) throws UnknownHostException, IOException, ClassNotFoundException {
		init();
		String data = "##clientid\r\nserverid\r\nother\r\n10942\r\n";
		byte bb[] = data.getBytes();
		System.out.println(bb.length);
		//oos.writeBytes(data);
		oos.write(bb);
		//oos.writeObject(invo);
		oos.flush();
		ois = new ObjectInputStream(socket.getInputStream());
		
		Invocation result = (Invocation) ois.readObject();
		
		invo.setResult(result.getResult());
	}
	
	public static <T> T getProxy(DsfConsumerBean dsfConsumerBean,String host,int port) throws ClassNotFoundException {
		
		final BaseClient client = new BaseClient(host,port);
		final Class clazz = Class.forName(dsfConsumerBean.getInterfaceName());
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Invocation invo = new Invocation();
				invo.setInterfaces(clazz);
				invo.setMethod(new org.dsf.common.protocol.Method(method.getName(),method.getParameterTypes()));
				invo.setParams(args);
				client.invoke(invo);
				return invo.getResult();
			}
		};
		T t = (T) Proxy.newProxyInstance(RPC.class.getClassLoader(), new Class[] {clazz}, handler);
		return t;
	}
}
