package test;

import org.dsf.rpc.RpcServerHandler;
import org.dsf.server.Server;
import org.dsf.server.impl.BaseServer;

public class RpcServerTest {
	public static void main(String []args) {
		Server server = new BaseServer();
		server.setHandler(new RpcServerHandler());
		
	}
}
