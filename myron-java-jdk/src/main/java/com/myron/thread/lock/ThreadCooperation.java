package com.myron.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程同步完全可以避免竞争状态的发生。但是有时候需要线程之间的互相协作。
 * 本例：演示使用条件便于线程间通信
 * 操作:通过Lock对象 newCondition()方法创建条件
 * condition.await()      当前线程释放锁并在此等待(取款时余额不足)，直到条件发生(存款线程)其他线程调用condition对象的signal()方法
 * 		通知当前线程后,当前线程从await()方法返回,并在返回前已经获取了锁
 * condition.signal()     唤起一个等待的线程(如取款线程)
 * condition.signalAll()  唤起所有等待的线程
 * @author Administrator
 *
 */
public class ThreadCooperation {
	private static 	Account account = new Account();
	public static void main(String[] args) {
	
		ExecutorService executor = Executors.newFixedThreadPool(4);
		executor.execute(new DepositTask(account));
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
				this.account.withdraw((int)(Math.random() * 10) + 10);
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
		private static Lock lock = new ReentrantLock();
		//获得Lock对象对应的Condition
		private static Condition condition = lock.newCondition();
		
		private int balance = 0;

		//取款
		public void withdraw(int amount) {
			lock.lock();
			try{
				while (balance < amount) {
					System.out.println(Thread.currentThread().getName() + "尝试取款：" + amount + "元   余额:" + balance + "不足");
					condition.await();//方法阻塞
				}
				balance -= amount;
				System.out.println(Thread.currentThread().getName() + "取款:  " + amount + "元  余额：" + balance);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
		//存钱
		public void deposit(int amount) {
			lock.lock();
			try {
				balance += amount;
				System.out.println(Thread.currentThread().getName() + "存款:" + amount + "元         余额" + balance);
				//通知正在等待的线程
				condition.signalAll();;
			} finally {
				lock.unlock();
			}
		}
		
	}

}
