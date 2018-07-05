package com.myron.socket.aio.demo;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result,
			AsyncTimeServerHandler attachment) {
		//接收新客户端的请求
		attachment.listener.accept(attachment, this);
		
		//预分配1MB的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		result.read(buffer, buffer, new ReadCompletionhandler(result));
		
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		
	}





	

}
