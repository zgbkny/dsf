package test;

import org.dsf.client.impl.BaseClient;
import org.dsf.common.DsfConsumerBean;
import org.dsf.common.DsfProviderBean;
import org.dsf.common.RPC;

import com.dsf.test.Echo;


public class ClientTest {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		/*DsfConsumerBean dsfConsumerBean = new DsfConsumerBean();
		dsfConsumerBean.setInterfaceName("com.dsf.test.Echo");
		dsfConsumerBean.setVersion("1.0.0");
		
		Echo echo = RPC.getProxy(dsfConsumerBean, "127.0.0.1", 2038   
		
		
		  2);
		
		System.out.println(echo.echo("hello,hello"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));*/
		/////////////////////////////////////////
		DsfConsumerBean dsfConsumerBean = new DsfConsumerBean();
		dsfConsumerBean.setInterfaceName("com.dsf.test.Echo");
		dsfConsumerBean.setVersion("1.0.0");
		String str =  "我";
		System.out.println(str.getBytes().length);
		
		Echo echo = BaseClient.getProxy(dsfConsumerBean, "localhost", 9000);
		
		System.out.println(echo.echo("hello,hello"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
		System.out.println(echo.echo("hellow,rod"));
	}

}
