package org.dsf.server;

public interface Server {
	public void stop();
	public void start();
	public boolean isRunning();
	public int getPort();
	public void setHandler(EventHandler handler);
}
