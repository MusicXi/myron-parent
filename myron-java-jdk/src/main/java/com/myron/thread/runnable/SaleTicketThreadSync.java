package com.myron.thread.runnable;

/**
 * 创建新执行线程的第二种方法:实现Runnable接口的run方法( )
 * 启动线程:在创建 Thread 时作为一个参数来传递并启动。new Thread(saleTicketThread).start();
 * 
 * 特点:实现Runnable,类中的属性将被多个线程共享 
 * 问题:多线程操作同一资源有可能出现资源同步问题,如剩余票数为负数
 */
public class SaleTicketThreadSync implements Runnable {
	
	//票总数
	private int ticket=5;
	
	/**
	 * 未设置同步
	 */
/*	@Override
	public void run() {
		for(int i=0; i<10; i++){ 
			if(this.ticket>0){
				try {
					Thread.sleep(50);//加入延迟,最后一张票还在处理时,未完成进行减操作,其他线程进入。误判还有票
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程的卖第"+(i+1)+"张票,剩余票总数："+--this.ticket);
			}
		}
		
	}*/
	
	/**
	 * 使用同步代码块解决同步问题
	 * 一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块
	 */
	@Override
	public void run() {
		for(int i=0; i<10; i++){ 
			/**
			 * 当一个线程访问object的一个synchronized(this)同步代码块时，
			 * 1.其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞
			 * 2.另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
			 */
			synchronized (this) {//this 一般将当前对象设置成同步对象
				if(this.ticket>0){
					try {
						Thread.sleep(50);//加入延迟,最后一张票还在处理时,未完成进行减操作,其他线程进入。误判还有票
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("同步代码块"+Thread.currentThread().getName()+"线程的卖第"+(i+1)+"张票,剩余票总数："+--this.ticket);
				}		
			}
		}
		
	}

	
}
