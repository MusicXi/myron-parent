package com.myron.socket.rpc.register;

import java.io.Serializable;
import java.util.List;

/**
 * 发送注册/订阅 的消息对象
 * 
 * @author lin.r.x
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type; // 消息类型：注册/订阅
	private String serviceAddress;
	private String interfaceName;
	private String version;
	private Integer timeout;
	
	private List<String> availableServiceAddresses; //可用服务地址列表

	public Message() {
		super();
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public List<String> getAvailableServiceAddresses() {
		return availableServiceAddresses;
	}

	public void setAvailableServiceAddresses(List<String> availableServiceAddresses) {
		this.availableServiceAddresses = availableServiceAddresses;
	}

	
	

}
