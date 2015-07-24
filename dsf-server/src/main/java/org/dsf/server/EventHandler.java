package org.dsf.server;

import org.dsf.net.*;

public interface EventHandler {
	public static final int ADD = 0;
	public static final int OK = 0;
	public int handleWrite(Connection c);
	public int handleRead(Connection c);
	public int handleAccept(Connection c);
}
