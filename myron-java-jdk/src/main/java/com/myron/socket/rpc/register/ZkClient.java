package com.myron.socket.rpc.register;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 模拟注册中心客户端
 * @author lin.r.x
 *
 */
public class ZkClient {
	private String host;
	private Integer port;
	private Socket socket = null;

	public ZkClient(String host, Integer port) {
		this.host = host;
		this.port = port;
		this.connection();
	}

	private void connection() {
		try {
			socket = new Socket(this.host, this.port);
			if (socket != null) {
				System.out.println("zk connect success!");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void register(Message message) throws IOException {
		
		// 将暴露成服务的主机地址,访问端口、接口类、接口实现、注册到注册中心
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(message);

		// 同步阻塞等待服务器返回应答，获取应答后返回
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		System.out.println("ZkClient " + input.readUTF());
		
		if (output != null) {
			output.close();
		}
		
		if (input != null) {
			input.close();
		}
	}
	
	public Message order(String serviceName) throws Exception {
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		Message msg = new Message();
		msg.setType("order");
		//msg.setServiceInterface(serviceInterface);
		msg.setInterfaceName(serviceName);
		output.writeObject(msg);
		
		// 同步阻塞等待服务器返回应答，获取应答后返回
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		
		//TODO 强转前类型判断
		Message message = (Message) input.readObject();
		
		if (output != null) {
			output.close();
		}
		
		if (input != null) {
			input.close();
		}
		
		return message;
	}


}
