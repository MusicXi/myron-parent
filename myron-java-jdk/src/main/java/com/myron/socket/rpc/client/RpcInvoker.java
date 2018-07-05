package com.myron.socket.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.myron.socket.rpc.bean.RpcException;
import com.myron.socket.rpc.bean.RpcRequest;
import com.myron.socket.rpc.bean.RpcResponse;
import com.myron.socket.rpc.register.ServiceDiscovery;

/**
 * 客户端的RpcInvoker 通过连接器RpcConnector 负责编码调用信息和发送调用请求到服务方并等待调用结果返回
 * 
 * @author Administrator
 *
 */
public class RpcInvoker<T> implements InvocationHandler {

	private ServiceDiscovery serviceDiscovery;
	private Class<T> serviceInterface;
	private String serviceVerison;

	public RpcInvoker(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// 确定要调用服务的目标主机
		String remoteAddress = serviceDiscovery.discover(serviceInterface.getName(), this.serviceVerison);

		// 创建 RPC连接
		String[] array = remoteAddress.split(":");
		String host = array[0];
		int port = Integer.parseInt(array[1]);
		RpcConnector connector = RpcConnector.newInstance(host, port);

		// 初始化 RPC请求对象 设置请求参数
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setServiceVersion(this.serviceVerison);
		request.setInterfaceName(serviceInterface.getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParameters(args);

		// 获取RPC响应对象 基于TCP的短连接,调用后立即释放
		RpcResponse response = connector.remoteInvoke(request);
		if (response.getException() != null) {
			if (response.getException() instanceof RpcException) {
				throw (RpcException) response.getException();
			} else {
				throw response.getException();				
			}
		}
		return response.getResult();

	}

	public Class<T> getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(Class<T> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public String getServiceVerison() {
		return serviceVerison;
	}

	public void setServiceVerison(String serviceVerison) {
		this.serviceVerison = serviceVerison;
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	

}
