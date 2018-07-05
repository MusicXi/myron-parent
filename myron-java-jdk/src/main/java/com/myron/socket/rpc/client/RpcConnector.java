package com.myron.socket.rpc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.myron.socket.rpc.bean.RpcProtocol;
import com.myron.socket.rpc.bean.RpcRequest;
import com.myron.socket.rpc.bean.RpcResponse;


public class RpcConnector {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private RpcConnector(Socket socket) {
		this.socket = socket;
	}
	
	public static RpcConnector newInstance(String host, int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(host, port);
		return new RpcConnector(socket);
	} 
	
	/**
	 * 服务框架要把本地进程内容的方法调用变为远程的方法调用，首先需要把调用所需的信息从服务调用端的内存对象变为二进制数据，然后通过网络
	 * 传到远程的服务提供端，再在服务端提供反序列化数据，得到调用的参数后进行相关调用
	 * @param serviceInterface
	 * @param methodName
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public Object remoteInvoke(String serviceInterface, String methodName,
			Class<?>[] parameterTypes, Object[] args)  {
		
			Object result;
			try {
				output = new ObjectOutputStream(this.socket.getOutputStream());
				RpcProtocol protocol = RpcProtocol.decode(serviceInterface, methodName, parameterTypes, args);
				//TODO java 序列化自身会有性能问题，序列化反序列化方式要考虑支持跨语言，注意序列化后的长度,考虑json作为序列化方式
				//Byte [] data = serialize();
				output.writeObject(protocol);
				// 4.同步阻塞等待服务器返回应答，获取应答后返回
				input = new ObjectInputStream(socket.getInputStream());
				result = input.readObject();
				return 	result;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return null;
		
	}
	
	public RpcResponse remoteInvoke(RpcRequest request)  {

		try {
			output = new ObjectOutputStream(this.socket.getOutputStream());
			
			//TODO java 序列化自身会有性能问题，序列化反序列化方式要考虑支持跨语言，注意序列化后的长度,考虑json作为序列化方式
			//Byte [] data = serialize();
			output.writeObject(request);
			// 同步阻塞等待服务器返回应答，获取应答后返回
			input = new ObjectInputStream(socket.getInputStream());
			RpcResponse response = (RpcResponse) input.readObject();
			return 	response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
		
	}
	
	public void close() {
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (this.socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
