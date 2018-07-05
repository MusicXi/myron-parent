package com.myron.rpc.server;

import java.io.IOException;

/**
 * 服务注册中心
 * @author Administrator
 *
 */
public interface Server {
	void stop();
	 
    void start() throws IOException;
 
    @SuppressWarnings("rawtypes")
	void register(Class serviceInterface, Class impl);
 
    boolean isRunning();
 
    int getPort();
}
