package com.myron.socket.rpc.register;

import java.io.IOException;

/**
 * 配置服务注册组件  
 * @author lin.r.x
 *
 */
public class ServiceRegistry {
	private ZkClient  zkClient;
	
	public ServiceRegistry(String host, Integer port) {
		this.zkClient = new ZkClient(host, port);
	}

	/**
	 * 注册服务
	 * @param interfaceName 服务名称
	 * @param serviceAddress RPC服务地址
	 * @throws IOException 
	 */
	public void register(String serviceName, String serverAddress) throws IOException {
		
		Message msg = new Message();
		String[] array = serviceName.split(":");

		if (array.length == 2) {
			msg.setInterfaceName(array[0]);
			msg.setVersion(array[1]);			
		} else {
			msg.setInterfaceName(array[0]);
			msg.setVersion(null);	
		}
		msg.setServiceAddress(serverAddress);
		msg.setType("register");
		zkClient.register(msg);
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}
	

}
