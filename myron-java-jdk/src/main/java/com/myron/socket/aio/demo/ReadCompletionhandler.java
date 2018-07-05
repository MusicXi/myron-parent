package com.myron.socket.aio.demo;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionhandler implements CompletionHandler<Integer, ByteBuffer>{

	private AsynchronousSocketChannel channel;
	
	
	public ReadCompletionhandler(AsynchronousSocketChannel channel) {
		if (this.channel == null) {
			this.channel = channel;			
		}
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String req = new String(body, "UTF-8");
			System.out.println("The time server recieve order: " + req);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		
	}

}
