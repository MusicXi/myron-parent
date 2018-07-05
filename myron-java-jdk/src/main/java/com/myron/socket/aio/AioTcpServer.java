package com.myron.socket.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioTcpServer implements Runnable { 
    private AsynchronousChannelGroup asyncChannelGroup;  
    private AsynchronousServerSocketChannel listener;  
  
    public AioTcpServer(int port) throws Exception { 
        ExecutorService executor = Executors.newFixedThreadPool(20); 
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor); 
        listener = AsynchronousServerSocketChannel.open(asyncChannelGroup).bind(new InetSocketAddress(port)); 
    } 
 
    public void run() { 
        try { 
         
       //  AioAcceptHandler acceptHandler = new AioAcceptHandler();
            listener.accept(listener, new AioAcceptHandler());  
         Thread.sleep(400000); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
         System.out.println("finished server");
        } 
    } 
 
    public static void main(String... args) throws Exception { 
        AioTcpServer server = new AioTcpServer(9008); 
        new Thread(server).start(); 
    } 
}
 

 
