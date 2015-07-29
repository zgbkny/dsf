package test;

import org.dsf.common.DsfProviderBean;
import org.dsf.rpc.RpcServerHandler;
import org.dsf.server.Server;
import org.dsf.server.handler.impl.ReuseEventHandler;
import org.dsf.server.impl.BaseServer;

import com.dsf.test.Echo;
import com.dsf.test.RemoteEcho;

public class RpcServerTest {
	public static void main(String []args) {
		DsfProviderBean dsfProviderBean = new DsfProviderBean();
		dsfProviderBean.setServiceInterface(Echo.class.getName());
		dsfProviderBean.setTarget(new RemoteEcho());
		dsfProviderBean.setServiceVersion("1.0.0");
		
		BaseServer server = new BaseServer();
		RpcServerHandler rpcServerHandler = new RpcServerHandler();
		rpcServerHandler.register(dsfProviderBean);
		//server.setHandler(rpcServerHandler);
		server.setHandler(new ReuseEventHandler());
		new Thread(server).start();
	}
}
