package com.myron.socket.aio.demo;

import java.io.IOException;

public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				// 使用默认值
			}
		}

		// 创建异步时间服务处理类
		AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
		new Thread(timeServer).start();

	}


}
