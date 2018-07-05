package com.myron.rpc.provider;

import java.io.IOException;
import com.myron.rpc.server.Server;
import com.myron.rpc.server.ServiceCenter;
import com.myron.rpc.service.HelloService;
import com.myron.rpc.service.impl.HelloServiceImpl;

public class RpcProvider {
 
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(HelloService.class, HelloServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("RpcProvider started..");
//        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
//        System.out.println(service.sayHi("tesddddt"));
    }
}