package com.dsf.test;

import org.dsf.common.DsfConsumerBean;
import org.dsf.common.DsfProviderBean;
import org.dsf.common.RPC;


public class ClientTest {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		DsfConsumerBean dsfConsumerBean = new DsfConsumerBean();
		dsfConsumerBean.setInterfaceName("com.dsf.test.Echo");
		dsfConsumerBean.setVersion("1.0.0");
		
		Echo echo = RPC.getProxy(dsfConsumerBean, "127.0.0.1", 20382);
		
		System.out.println(echo.echo("hello,hello"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
	}

}
