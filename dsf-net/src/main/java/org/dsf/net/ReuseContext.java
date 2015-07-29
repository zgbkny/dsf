package org.dsf.net;

/***
 * 用于tcp复用的session层
 * @author william.ww
 * 
 * 包结构：clientId\r\nserverId\r\n扩展\r\ndatalen\r\ndata
 */
public class ReuseContext {
	
	public static final int STATE_INIT = 0;
	public static final int STATE_CLIENTID = 1;
	public static final int STATE_SERVERID = 2;
	public static final int STATE_OTHER = 3;
	public static final int STATE_DATALEN = 4;
	public static final int STATE_DATA = 5;
 	
	private String 			clientId;		// 客户端对应的id
	private String 			serverId;		// 服务端对应的id
	private String			other;			// 扩展,不能包含\r\n关键字，在输入的时候需要校验
	private Long 			dataLen;		// 数据包的长度
	
	private Object 			otherObject;	// other对应扩展功能的object
	
	private int  			state; 			// 当前解析的状态
	
	
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
	public Long getDataLen() {
		return dataLen;
	}
	public void setDataLen(Long dataLen) {
		this.dataLen = dataLen;
	}
	
	public Object getOtherObject() {
		return otherObject;
	}
	
	public void setOtherObject(Object otherObject) {
		this.otherObject = otherObject;
	}
}
