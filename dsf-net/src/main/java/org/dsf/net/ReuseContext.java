package org.dsf.net;

/***
 * 用于tcp复用的session层
 * @author william.ww
 * 
 * 包结构：datalen\r\n\r\nclientId\r\nserverId\r\n扩展\r\ndata
 * 说明：datalen，表示的是整个数据包体的长度，可加密，如果包体加密，那么就表示整个数据
 *	   clientId\r\nserverId\r\n扩展\r\ndata也可加密，但这一部分得作为整体加密
 * 	
 */
public class ReuseContext {
	
	public static final byte CR = 13;
	public static final byte LF = 10;
	public static final byte[] CRLF = { CR, LF };
	public static final byte[] CRLFCRLF = { CR, LF, CR, LF };
	
	
	
	public static final int STATE_INIT = 0;
	public static final int STATE_DATALEN = 4;
	public static final int STATE_DATA = 5;
	public static final int STATE_CLIENTID = 1;
	public static final int STATE_SERVERID = 2;
	public static final int STATE_OTHER = 3;
	public static final int STATE_DONE = 6;
	public static final int STATE_ERR = 7;
 	
	
	private int 			dataLen;		// 数据包的长度
	private byte[]			body;			// 数据包体部分，clientId\r\nserverId\r\n扩展\r\ndata
	
	private String 			clientId;		// 客户端对应的id
	private String 			serverId;		// 服务端对应的id
	private String			other;			// 扩展,不能包含\r\n关键字，在输入的时候需要校验
	private Object 			otherObject;	// other对应扩展功能的object
	
	private int  			state; 			// 当前解析的状态
	
	
	public ReuseContext() {
		state = STATE_INIT;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public int getDataLen() {
		return dataLen;
	}
	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
	
	public Object getOtherObject() {
		return otherObject;
	}
	
	public void setOtherObject(Object otherObject) {
		this.otherObject = otherObject;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
}
