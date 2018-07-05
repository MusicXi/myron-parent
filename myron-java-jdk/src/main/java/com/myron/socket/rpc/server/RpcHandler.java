package com.myron.socket.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

import com.myron.socket.rpc.bean.RpcException;
import com.myron.socket.rpc.bean.RpcRequest;
import com.myron.socket.rpc.bean.RpcResponse;

public class RpcHandler implements Runnable {
	private Socket socket = null;
	private Map<String, Object> serviceList;
	
	public RpcHandler(Socket socket, Map<String, Object> serviceList) {
		this.socket = socket;
		this.serviceList = serviceList;
	}
	
	private Object handle(RpcRequest request) throws Exception {
		// 获取服务对象:接口名+版本
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();
    	if (! "".equals(serviceVersion)) {
			serviceName += "-" + serviceVersion;
		}
		Object serviceImpl = serviceList.get(serviceName);
		if (serviceImpl == null) {
			throw new RpcException(String.format("can not find service bean by key: %s", serviceName));
		}
		
		// 获取反射调用所需的参数
		String methodName = request.getMethodName();
		Object[] args  = request.getParameters();
		Class<?>[] parameterTypes = request.getParameterTypes();
		
		// 执行反射调用
		Method method = serviceImpl.getClass().getMethod(methodName, parameterTypes);
		return method.invoke(serviceImpl, args);
	}

	@Override
	public void run() {
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			// 获取RPC请求对象 对象流用于对已经存在的输入/输出流进行包装，以便在原始数据流中过滤数据
			input = new ObjectInputStream(socket.getInputStream());
			RpcRequest request = (RpcRequest) input.readObject();
			
			// 创建并初始化 RPC 响应对象
	        RpcResponse response = new RpcResponse();
	        response.setRequestId(request.getRequestId());
	        
	        // 处理RPC请求内容 及调用异常处理
	        try {
	        	Object result = this.handle(request);
	        	response.setResult(result);	        	
	        } catch (Exception e) {
	        	response.setException(e);
	        }	
	        
			// 写入RPC响应对象
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(response);	
		}  catch (Exception e) {
			e.printStackTrace();
			//LOG.error("Error in RPCServer", e);
		}  finally {
			this.close(input, output, socket);
		}
		
	}
	
	private void close(ObjectInputStream input, ObjectOutputStream output, Socket socket) {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (this.socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

