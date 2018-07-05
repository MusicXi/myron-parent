package com.myron.socket.rpc.register;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟注册中心服务端
 * @author lin.r.x
 *
 */
public class ZkServer {
	private static ExecutorService executor = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	//服务注册列表
	private static final HashMap<String, List<String>> serviceRegistry = new HashMap<String, List<String>>();

	private static Integer clientPort = 2181;
	private static String address = "localhost";
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) throws IOException {
		run();
	}
	
	public static void run() throws IOException {
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(address, clientPort));
		System.out.println("注册中心启动,地址:" + address + ":" + clientPort);
		try {
			while (true) {
				//创建连接到到客户端的套接字 Socket socket = serverSocket.accept();
				//监听客户端的TCP连接，接到TCP连接后将其封装成task，由线程池执行
				executor.execute(new ServiceTask(serverSocket.accept()));
			}
		} finally {
			serverSocket.close();
		}
	}
	
	private static class ServiceTask implements Runnable {
		Socket client = null;

		public ServiceTask(Socket client) {
			this.client = client;
		}
		
		public void run() {
			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			//获取客户端的主机名和IP地址
//			InetAddress inetAddress = client.getInetAddress();
//			System.out.println("client's host name is" + inetAddress.getHostName());
//			System.out.println("client's IP Address is" + inetAddress.getHostAddress());
			try {
				while (true) {
					input = new ObjectInputStream(client.getInputStream());
					//TODO　增加强转类型判断
					Message msg = (Message) input.readObject();
					//处理注册/订阅任务
					if ("register".equals(msg.getType())) {
						output = new ObjectOutputStream(client.getOutputStream());
						register(msg, output);
					} else if ("order".equals(msg.getType())) {
						output = new ObjectOutputStream(client.getOutputStream());
						order(msg, output);
					}					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
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
				if (client != null) {
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		

		private void register(Message msg, ObjectOutputStream output) throws IOException {
			String interfaceName = msg.getInterfaceName();
			// 保存RPC服务地址信息
			List<String> addressList = serviceRegistry.get(interfaceName);
			if (! (addressList == null || addressList.isEmpty())) {
				addressList.add(msg.getServiceAddress());
			} else {
				addressList = new ArrayList<String>();
				addressList.add(msg.getServiceAddress());
				serviceRegistry.put(interfaceName, addressList);
			}

			System.out.println(interfaceName + " => " + msg.getServiceAddress() + "注册成功");
			String template = "register success: %s => %s";
			String info = String.format(template, interfaceName, msg.getServiceAddress());
			output.writeUTF(info);
		};
		
		private void order(Message msg, ObjectOutputStream output) throws IOException {
			String interfaceName = msg.getInterfaceName();
			List<String> addressList = serviceRegistry.get(interfaceName);
			msg.setAvailableServiceAddresses(addressList);
			output.writeObject(msg);
			System.out.println(interfaceName + "订阅成功");
		}
	}
}
