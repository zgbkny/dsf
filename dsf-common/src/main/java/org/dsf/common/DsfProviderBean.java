package org.dsf.common;

import org.dsf.common.support.Server;

public class DsfProviderBean {
	private String serviceInterface;
	private String serviceName;
	private String serviceVersion;
	private Object target;
	
	
	public String getServiceInterface() {
		return serviceInterface;
	}
	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	
}
