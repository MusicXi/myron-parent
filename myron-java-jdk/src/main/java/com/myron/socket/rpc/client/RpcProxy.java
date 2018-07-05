package com.myron.socket.rpc.client;

import java.lang.reflect.Proxy;
import com.myron.socket.rpc.register.ServiceDiscovery;

/**
 * RPC 代理（用于创建 RPC 服务代理）
 * @author lin.r.x
 *
 */
public class RpcProxy {
	
	private ServiceDiscovery serviceDiscovery;

	public <T> Object newProxyInstance(final Class<T> serviceInterface) {
		return this.newProxyInstance(serviceInterface, "");
	}
	
	public <T> Object newProxyInstance(final Class<T> serviceInterface, final String serviceVersion) {
		RpcInvoker<T> invoker = new RpcInvoker<T>(serviceDiscovery);
		invoker.setServiceInterface(serviceInterface);
		invoker.setServiceVerison(serviceVersion);
		//动态生成实现类
		return Proxy.newProxyInstance(serviceInterface.getClassLoader(),
				new Class<?>[] { serviceInterface }, invoker);
		
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}
	
	
	

}
	