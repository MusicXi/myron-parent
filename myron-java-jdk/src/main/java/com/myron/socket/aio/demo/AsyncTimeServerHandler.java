package com.myron.socket.aio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {
	private Integer port;
	//异步服务通道
	AsynchronousServerSocketChannel listener;
	CountDownLatch latch;
	
	public AsyncTimeServerHandler(Integer port) {
		this.port = port;
		try {
			listener = AsynchronousServerSocketChannel.open();
			//绑定监听端口
			listener.bind(new InetSocketAddress(port));
			System.out.println("The time server is start in port:" + this.port);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	public void doAccept() {
		//等待连接，并注册CompletionHandler处理内核完成后的操作。  
		AcceptCompletionHandler handler = new AcceptCompletionHandler();
		//listener.accept(listener, handler);
		listener.accept(this, handler);
	}
	
	
	

}
