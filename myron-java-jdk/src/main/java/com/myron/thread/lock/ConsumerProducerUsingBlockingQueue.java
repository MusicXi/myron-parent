package com.myron.thread.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用阻塞队列BlockingQueue(扩展java.util.Queue)实现   生产者消费者问题
 * <ul>实现类：
 * <li>固定容量：ArrayBlockingQueue</li>
 * <li>无固定容量：LinkedBlockingQueue  链表实现</li>
 * <li>无固定容量：PriorityBlockingQueue 优先队列</li>
 * 
 * </ul>
 * 同步的方法：
 * put()  队列头添加元素 
 * take() 队列尾删除元素
 * @author Administrator
 *
 */
public class ConsumerProducerUsingBlockingQueue {

	public static void main(String[] args) {

		ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<Integer>(5);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ConsumerTask(buffer));
		executor.execute(new ProducerTask(buffer));


		executor.shutdown();
	}
	public static class ConsumerTask implements Runnable{
		private ArrayBlockingQueue<Integer> buffer;
		
		public ConsumerTask(ArrayBlockingQueue<Integer> buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			int i = 20;
			while (i-- > 0) {
				try {
					int value = buffer.take();//阻塞队列元素为空时,take方法进入阻塞状态
					System.out.println(Thread.currentThread().getName() +"read buffer:" + value + " " + buffer);
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public static class ProducerTask implements Runnable{
		private ArrayBlockingQueue<Integer> buffer;
		
		public ProducerTask(ArrayBlockingQueue<Integer> buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			int i = 20;
			while (i--  > 0) {
				try {
					buffer.put(i);//队列元素达到容量大小，put方法进入阻塞。对于非固定容量的LinkedBlockingQueue和PriorityBlockingQueue 永远不会阻塞
					System.out.println(Thread.currentThread().getName() +"write " + i + " " + buffer);
					Thread.sleep((int) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	

}
