package com.myron.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static int port = 8000;
	private static int count = 0;
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		ServerSocket server;
		Socket socket;
		DataInputStream inputFromClient;
		DataOutputStream outputToClient;
		try {
			//create a server socket
			server = new ServerSocket(port);
			//listent for a connection request
		
			System.out.println("Server Run!");
			while (true) {
				socket = server.accept();
				count++;
				System.out.println("第" + count + "次监听请求");
				// create data input and output streams
				inputFromClient = new DataInputStream(socket.getInputStream());
				outputToClient = new DataOutputStream(socket.getOutputStream());
				//receive radis from the client
				double radis = inputFromClient.readDouble();
				
				//compute area
				double area = radis * radis * Math.PI;
				
				//send area back to the client
				outputToClient.writeDouble(area);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
	

		

	}

}
