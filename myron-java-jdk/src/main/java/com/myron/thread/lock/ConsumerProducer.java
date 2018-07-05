package com.myron.thread.lock;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过向缓存区队列读写数据演示多线程   生产者消费者问题
 * @author Administrator
 *
 */
public class ConsumerProducer {

	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ConsumerTask(buffer));
		executor.execute(new ProducerTask(buffer));


		executor.shutdown();
	}
	public static class ConsumerTask implements Runnable{
		private Buffer buffer;
		
		public ConsumerTask(Buffer buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			int i = 20;
			while (i-- > 0) {
				buffer.read();
				try {
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public static class ProducerTask implements Runnable{
		private Buffer buffer;
		
		public ProducerTask(Buffer buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			int i = 20;
			while (i--  > 0) {
				buffer.write(i);
				try {
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	//缓冲区对象(本质上先进先出队列)
	private static class Buffer {
	
		private static final int CAPACITY = 5;	//缓冲区容量大小
		private LinkedList<Integer> queue = new LinkedList<Integer>();	//存储数据的队列
		//lock
		private static Lock lock = new ReentrantLock();
		//conditions
		private static Condition notEmpty = lock.newCondition();
		private static Condition notFull = lock.newCondition();
		
		public int read() {
			int value = 0;
			lock.lock();
			try {
				while (queue.isEmpty()) {
					System.out.println(Thread.currentThread().getName() +"try read buffer:" +"缓冲区无数据,等待notEmpry状态");
					notEmpty.await();
				}
				value = queue.remove();
				System.out.println(Thread.currentThread().getName() +"read buffer:" + value + " " + queue);
			
				notFull.signal(); //唤起等待notFull线程恢复执行
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			return value;
		}
		
		public void write(int i) {
			lock.lock();
			try {
				while (queue.size() == CAPACITY) {
					System.out.println(Thread.currentThread().getName() +"try write " + i +" 缓存区已满,等待notFull状态");
					notFull.await();
				}
				queue.offer(i);
				System.out.println(Thread.currentThread().getName() +"write " + i + " " + queue);
				notEmpty.signal();//唤起在notEmpty状态上等待的线程
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
