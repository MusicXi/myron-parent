package com.myron.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioTcpClient {
	private AsynchronousChannelGroup asyncChannelGroup;

	public AioTcpClient() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
	}

	private final CharsetDecoder decoder = Charset.forName("GBK").newDecoder();

	public void start(final String ip, final int port) throws Exception {
		// 启动20000个并发连接，使用20个线程的池子
		for (int i = 0; i < 20000; i++) {
			try {
				AsynchronousSocketChannel connector = null;
				if (connector == null || !connector.isOpen()) {
					connector = AsynchronousSocketChannel
							.open(asyncChannelGroup);
					connector
							.setOption(StandardSocketOptions.TCP_NODELAY, true);
					connector.setOption(StandardSocketOptions.SO_REUSEADDR,
							true);
					connector.setOption(StandardSocketOptions.SO_KEEPALIVE,
							true);
					connector.connect(new InetSocketAddress(ip, port),
							connector, new AioConnectHandler(i));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String... args) throws Exception {
		AioTcpClient client = new AioTcpClient();
		client.start("localhost", 9008);
	}
}