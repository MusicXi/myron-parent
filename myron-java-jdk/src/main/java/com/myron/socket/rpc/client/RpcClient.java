package com.myron.socket.rpc.client;

import com.myron.rpc.service.HelloService;
import com.myron.socket.rpc.register.ServiceDiscovery;

/**
 *  负责导入（import）远程接口的代理实现  
 * @author lin.r.x
 *
 */
public class RpcClient {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object refer(Class serviceInterface) {
		return new RpcProxy().newProxyInstance(serviceInterface);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object refer(Class serviceInterface, String version) {
		return new RpcProxy().newProxyInstance(serviceInterface, version);
	}
	
	public static void main(String[] args) throws InterruptedException {
		RpcProxy proxy = new RpcProxy();
		ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1", 2181);;
		proxy.setServiceDiscovery(serviceDiscovery);
		
		HelloService helloService = (HelloService) proxy.newProxyInstance(HelloService.class, "0.0.1");
		
		//像本地实现一样使用接口实现
		//HelloService helloService = (HelloService) RpcClient.refer(HelloService.class, "0.0.1");
		while (true) {
			Thread.sleep(5000);
			System.out.println(helloService.sayHi("jackddd"));
			System.out.println(helloService.plus(1, 2));			
		}
	}
}
