package com.dsf.test;

import org.dsf.common.DsfProviderBean;
import org.dsf.common.RPC;
import org.dsf.common.support.Server;

public class ServerTest {
	public static void main(String args[]) {
		DsfProviderBean dsfProviderBean = new DsfProviderBean();
		dsfProviderBean.setServiceInterface(Echo.class.getName());
		dsfProviderBean.setTarget(new RemoteEcho());
		dsfProviderBean.setServiceVersion("1.0.0");
		
		Server server =  new RPC.RPCServer();
		server.register(dsfProviderBean);
		server.start();
	}
}
