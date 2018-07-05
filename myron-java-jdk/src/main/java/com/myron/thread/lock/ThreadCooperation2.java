package com.myron.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Java5线程通信使用对象的内置监视器编程实现的
 * 监视器(monitor)是一个互相排斥且具备同步能力的对象。监视器一个时间点,只能有一个线程执行一个方法
 * 本例：通过对象内置监视器来演示线程间通信
 * 步骤:加锁通过方法 或块上使用synchronized关键字实现,从而进入对象的监视器
 * object.wait()      当前线程处于等待,释放锁。这样其他一些监视器中的线程可以获取
 * object.notify()     通知一个等待的线程重新获取锁并恢复执行
 * object.notifyAll()  唤起所有等待的线程
 * 注意：wait()、notify()、notifyAll()必须在这些方法的接收对象的同步方法或同步块中调用,否则会出现
 * IllegalMonitorStateException异常 (非法监视状态异常,)
 * 
 * @author Administrator
 *
 */
public class ThreadCooperation2 {
	private static 	Account account = new Account();
	public static void main(String[] args) {
	
		ExecutorService executor = Executors.newFixedThreadPool(4);
		executor.execute(new DepositTask(account));
		executor.execute(new DepositTask(account));
		executor.execute(new WithdrawTask(account));
		executor.execute(new WithdrawTask(account));
		
		executor.shutdown();
	}
	
	//存钱线程
	public static class DepositTask implements Runnable {
		private Account account;
		
		public DepositTask(Account account) {
			this.account = account;
		}

		@Override
		public void run() {
			int i = 0;
			while (i++ < 100) {
				System.out.println(Thread.currentThread().getName() +  " " + i);
				this.account.deposit((int)(Math.random() * 10) + 1);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	//取钱线程
	public static class WithdrawTask implements Runnable {
		private Account account;
			
		public WithdrawTask(Account account) {
			this.account = account;
		}


		@Override
		public void run() {
			int i = 0;
			while (i++ < 100) {
				System.out.println(Thread.currentThread().getName() +  " " + i);
				this.account.withdraw((int)(Math.random() * 10) + 5);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
	}
	
	private static class Account {
		//显式定义Lock对象
		//private static Lock lock = new ReentrantLock();
		//获得Lock对象对应的Condition
		//private static Condition condition = lock.newCondition();
		
		private int balance = 0;

		//取款
		public synchronized void withdraw(int amount) {
	
			try{
				while (balance < amount) {
					System.out.println(Thread.currentThread().getName() + "尝试取款：" + amount + "元   余额:" + balance + "不足");
					//condition.await();//方法阻塞
					this.wait();
				}
				balance -= amount;
				System.out.println(Thread.currentThread().getName() + "取款:  " + amount + "元  余额：" + balance);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//lock.unlock();
			}
		}
		
		//存钱
		public synchronized void deposit(int amount) {
			//lock.lock();
			try {
				balance += amount;
				System.out.println(Thread.currentThread().getName() + "存款:" + amount + "元         余额" + balance);
				//通知正在等待的线程
				//condition.signalAll();;
				this.notifyAll();
			} finally {
				//lock.unlock();
			}
		}
		
	}

}
