package org.dsf.server.handler;

import org.dsf.net.*;

public interface EventHandler {
	public static final int ERR = -1;
	public static final int OK  =  0;
	public static final int ADD =  1;
	public static final int DEL =  2;
	
	public EventHandler preHandler  = null;
	public EventHandler nextHandler = null;
	public int handleWrite(Connection c);
	public int handleRead(Connection c);
	public int handleAccept(Connection c);
}
