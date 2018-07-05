package com.myron.socket.rpc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.myron.socket.rpc.register.ServiceRegistry;

/**
 * RPC 服务器(用于发布RPC服务)  
 * @author lin.r.x
 *
 */
public class RpcServer {
	/**
	 * ExecutorService继承自Executor，它的目的是为我们管理Thread对象，从而简化并发编程，
	 * Executor使我们无需显示的去管理线程的生命周期，是JDK 5之后启动任务的首选方式。 
	 */
	private static ExecutorService executor = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	/**
	 * 存放 服务名 与服务对象 之间的映射关系
	 */
	private static final HashMap<String, Object> serviceList = new HashMap<String, Object>();
	
	private static final int DEFAULT_PORT = 20880;
	
	private String serverAddress;			//127.0.0.1:20880
	private ServiceRegistry serviceRegistry;
		
	public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
		super();
		this.serverAddress = serverAddress;
		this.serviceRegistry = serviceRegistry;
	}

	/**
	 * 负责导出（export）远程接口 
	 * @param serverInterface
	 * @param impl
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void export(Class serviceInterface, Object impl, String serviceVersion) throws IOException {
		String serviceName = serviceInterface.getName() + "-" + serviceVersion;
		this.serviceRegistry.register(serviceName, this.serverAddress);
		serviceList.put(serviceName, impl);  //模拟本地spring上下文   保存 rpc service bean
	}
	
	public void start() throws IOException {
		int port = getServerPort();
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("RpcServer started on " + this.serverAddress);
		try {
			while (true) {
				executor.execute(new RpcHandler(serverSocket.accept(), serviceList));//一个线程对应一个请求
			}			
		} finally {
			serverSocket.close();
		}
	}
	
	/**
	 * 获取服务端口
	 * @return
	 */
	private int getServerPort() {
		String[] address = this.serverAddress.split(":"); 
		if (address.length == 2) {
			return Integer.parseInt(address[1]);
		} else {
			return DEFAULT_PORT;
		}
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	
}
