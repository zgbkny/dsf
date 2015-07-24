package org.dsf.rpc;

import java.nio.channels.SocketChannel;

import org.dsf.net.Connection;
import org.dsf.server.EventHandler;

public class RpcServerHandler implements EventHandler {

	@Override
	public int handleWrite(Connection c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int handleRead(Connection c) {
		// TODO Auto-generated method stub
		SocketChannel sc = c.getSc();
		
		return 0;
	}

	@Override
	public int handleAccept(Connection c) {
		// TODO Auto-generated method stub
		return handleRead(c);
	}

}
