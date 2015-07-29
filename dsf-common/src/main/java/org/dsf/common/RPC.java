package org.dsf.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.dsf.common.protocol.Invocation;
import org.dsf.common.support.Client;
import org.dsf.common.support.Listener;
import org.dsf.common.support.Server;


public class RPC {
	
	public static class RPCServer implements Server{
		private int port = 20382;
		private Listener listener; 
		private boolean isRuning = true;
		
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
		@Override
		public void call(Invocation invo) {
			System.out.println(invo.getClass().getName());
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
		}
		@Override
		public void register(Class interfaceDefiner, Class impl) {
			try {
				this.serviceEngine.put(interfaceDefiner.getName(), impl.newInstance());
				System.out.println(serviceEngine);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		@Override
		public void start() {
			System.out.println("启动服务器");
			listener = new Listener(this);
			this.isRuning = true;
			listener.start();
		}
		@Override
		public void stop() {
			this.setRuning(false);
		}
		@Override
		public boolean isRunning() {
			return isRuning;
		}

		@Override
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
		
	}
}
