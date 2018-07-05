package com.myron.rpc.client;

import java.net.InetSocketAddress;

import com.myron.rpc.service.HelloService;

public class RpcTest {

	public static void main(String[] args) {
      HelloService service = RpcClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
      System.out.println(service.sayHi("tdddesdddt"));
	}

}
