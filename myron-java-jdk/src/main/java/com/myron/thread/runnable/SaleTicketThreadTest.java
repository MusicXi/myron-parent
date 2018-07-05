package com.myron.thread.runnable;

/**
 * 本例说明：通过买票程序，演示创建新执行线程的第二种方法:实现Runnable接口
 */
public class SaleTicketThreadTest {
	public static void main(String[] args) {
		//1.实现Runnable接口的线程类,该类实现 run 方法
		
		//2.创建线程
		//SaleTicketThread mt=new SaleTicketThread();//未实现同步
		//SaleTicketThreadSync saleTicket1=new SaleTicketThreadSync();//通过同步代码块实现
		SaleTicketThreadSyncFn saleTicket=new SaleTicketThreadSyncFn();//通过同步方法实现
		
		//3.启动线程(同时启动三个线程)
		//使用实现接口 Runnable 的对象创建一个线程时，启动该线程将导致在独立执行的线程中调用对象的 run 方法。 
		new Thread(saleTicket,"A").start();//在创建 Thread 时作为一个参数来传递并启动
		new Thread(saleTicket,"B").start();
		new Thread(saleTicket,"C").start();
	}

}
