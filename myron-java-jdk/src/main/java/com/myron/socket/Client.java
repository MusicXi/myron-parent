package com.myron.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
	private static String address = "localhost";
	private static int port = 8000;
	
	public static void main(String[] args)  {
		try {
			Socket client = new Socket(address, port);
			System.out.println("连接服务器：" + address + ":" + port);
			// create data input and output streams
			DataOutputStream outputToServer = new DataOutputStream(client.getOutputStream());
		
			Double radis = 2.0;
			System.out.println("提交请求：radis" + radis);
			outputToServer.writeDouble(radis);
			System.out.println("同步阻塞,等待服务器返回应答，获取应答后返回");
			DataInputStream inputFromServer = new DataInputStream(client.getInputStream());
			Double area = inputFromServer.readDouble();
			System.out.println("读取服务端数据"+area);
			//Thread.sleep(5000);
			client.close();
				

				

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
/*		if (client != null) {
			client.close();
		}
		if (outputToServer != null) {
			outputToServer.close();
		}
		if (inputFromServer != null) {
			inputFromServer.close();
		}*/
	}

}
