package org.dsf.common.support;

import org.dsf.common.DsfProviderBean;
import org.dsf.common.protocol.Invocation;

public interface Server {
	public void stop();
	public void start();
	public void register(Class interfaceDefiner,Class impl);
	public void call(Invocation invo);
	public boolean isRunning();
	public int getPort();
	public void register(DsfProviderBean dsfProviderBean);
}
