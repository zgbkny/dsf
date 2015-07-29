package org.dsf.server;

import org.dsf.server.handler.EventHandler;

public interface Server {
	public void stop();
	public void start();
	public boolean isRunning();
	public int getPort();
	public void setHandler(EventHandler handler);
}
