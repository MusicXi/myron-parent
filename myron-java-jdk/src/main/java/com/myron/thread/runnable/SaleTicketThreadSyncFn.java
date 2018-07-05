package com.myron.thread.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建新执行线程的第二种方法:实现Runnable接口的run方法( )
 * 启动线程:在创建 Thread 时作为一个参数来传递并启动。new Thread(saleTicketThread).start();
 * 
 * 特点:实现Runnable,类中的属性将被多个线程共享 
 * 问题:多线程操作同一资源有可能出现资源同步问题,如剩余票数为负数
 */
public class SaleTicketThreadSyncFn implements Runnable {
	private static Logger logger=LoggerFactory.getLogger(SaleTicketThreadSyncFn.class);
	//票总数
	private int ticket=10;
	
	/**
	 * 使用同步代方法解决同步问题
	 */
	@Override
	public void run() {
		for(int i=0; i<50; i++){ 			
			saleTicket(i+1);
		}
	}
	
	/**
	 * 
	 * @param i 第几次卖票
	 */
	public synchronized void saleTicket(int i){
		if(this.ticket>0){
			try {
				Thread.sleep(50);//加入延迟,最后一张票还在处理时,未完成进行减操作,其他线程进入。误判还有票
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("同步方法:线程{}第{}次卖票 ,剩余{}张票",Thread.currentThread().getName(),i,--this.ticket);
			//System.out.println("同步代码块"+Thread.currentThread().getName()+"线程的卖票,剩余票总数："+--this.ticket);
		}			
	}

}
