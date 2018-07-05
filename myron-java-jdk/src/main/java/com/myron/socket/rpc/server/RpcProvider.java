package com.myron.socket.rpc.server;

import java.io.IOException;

import com.myron.rpc.service.HelloService;
import com.myron.rpc.service.impl.HelloServiceImpl;
import com.myron.socket.rpc.register.ServiceRegistry;

public class RpcProvider {
 
    public static void main(String[] args) throws IOException {
    	ServiceRegistry serviceRegistry = new ServiceRegistry("127.0.0.1", 2181);
    	RpcServer rpcServer = new RpcServer("127.0.0.1:20880", serviceRegistry);
    	
    	HelloService helloServiceImpl = new HelloServiceImpl();
    	//在20880端口暴露服务,并注册服务到注册中心
    	rpcServer.export(HelloService.class, helloServiceImpl, "0.0.1");
    	rpcServer.start();
    }
}