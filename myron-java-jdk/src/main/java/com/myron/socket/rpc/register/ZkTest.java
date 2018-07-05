package com.myron.socket.rpc.register;

import java.io.IOException;

import com.myron.rpc.service.HelloService;

/**
 * zk测试
 * @author lin.r.x
 *
 */
public class ZkTest {

	public static void main(String[] args) throws Exception {
		testRegister();
		testOrder();
	}
	
	public static void testRegister() {
		ZkClient  zkClient = new ZkClient("localhost", 2181);
		Message msg = new Message();
		msg.setInterfaceName(HelloService.class.getName());
		msg.setServiceAddress("localhost:20880");
		try {
			zkClient.register(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			zkClient.close();
		}
	}
	
	public static void testOrder() throws Exception {
		ZkClient  zkClient = new ZkClient("localhost", 2181);
		try {
			Message msg = zkClient.order(HelloService.class.getName());
			System.out.println(msg.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			zkClient.close();
		}
	}

}
