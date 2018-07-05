package com.myron.thread.runnable;

/**
 * 创建新执行线程的第二种方法:实现Runnable接口的run方法( ) 启动线程:在创建 Thread 时作为一个参数来传递并启动。new
 * Thread(saleTicketThread).start();
 * 
 * 特点:实现Runnable,类中的属性将被多个线程共享 问题:多线程操作同一资源有可能出现资源同步问题,如剩余票数为负数
 */
public class SaleTicketThread implements Runnable {

	// 票总数
	private int ticket = 5;

	/**
	 * 未设置同步
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if (this.ticket > 0) {
				try {
					Thread.sleep(50);// 加入延迟,最后一张票还在处理时,未完成进行减操作,其他线程进入。误判还有票
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "线程的卖第"
						+ (i + 1) + "张票,剩余票总数：" + --this.ticket);
			}
		}

	}

}
