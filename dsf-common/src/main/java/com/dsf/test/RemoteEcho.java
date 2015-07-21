package com.dsf.test;

public class RemoteEcho implements Echo {

	@Override
	public String echo(String str) {
		// TODO Auto-generated method stub
		return "echo from remote:" + str;
	}

	@Override
	public String doSth(String str) {
		// TODO Auto-generated method stub
		return str + " is done";
	} 

}
