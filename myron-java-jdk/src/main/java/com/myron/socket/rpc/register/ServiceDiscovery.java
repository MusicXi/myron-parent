package com.myron.socket.rpc.register;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 服务发现
 * @author lin.r.x
 *
 */
public class ServiceDiscovery {
	private String host;
	private Integer port;
	private ZkClient zkClient;
	private final Map<String, List<String>> addressCache = new HashMap<String, List<String>>();
	
	public ServiceDiscovery(String host, Integer port) {
		this.host = host;
		this.port = port;
		//TODO 服务调用者和注册中心保持长连接，session会话,这样才能获得注册中心的sever的事件通知
		this.zkClient = new ZkClient(host, port);
	}

	private String chooseTargetHost(List<String> addresses) {
		//TODO 根据RPC请求主机 和RPC响应主机 的最优路径 通过ip地址判断
		// 同一机架上的主机
		// 同一机房不同机架上
		// 同一数据中心不同机房
		
		// 采用随机策略
		Random random = new Random();
		int index = random.nextInt(addresses.size());
		return addresses.get(index);
	} 
	
	/**
	 * 实际上不是每次调用都通过注册中心查找可用地址,而是把调用地址缓存在本地,当有变化有变化时主动从
	 * 服务注册中心发起通知,告诉调用者可用服务提供者列表的变化。
	 * @param interfaceName
	 * @param version
	 * @return RPC 服务地址
	 * @throws IOException 
	 */
	public String discover(String interfaceName, String version) throws Exception {
		String serviceName = interfaceName;
		if (version != null && version.length() != 0) {
			serviceName += "-" + version;
		}
		
		// 从本地缓存服务列表中查找服务信息
		List<String> addresses = this.addressCache.get(serviceName);
		if (addresses == null || addresses.isEmpty()) {
			// 从注册订阅中心获取可用RPC服务地址列表    更新本地缓存PRC服务列表
			addresses = this.listAvailableAddresses(serviceName);
		}
		return this.chooseTargetHost(addresses);
	}
	
	private List<String> listAvailableAddresses(String serviceName) throws Exception {
		Message msg = zkClient.order(serviceName);
		List<String> addresses = msg.getAvailableServiceAddresses();
		this.addressCache.put(serviceName, addresses);
		return addresses;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	
}
