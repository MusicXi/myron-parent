package com.myron.thread.lock.readwrite;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class TestReadWriteLock {
	private static Cache cache = new Cache();
	private static Random random = new Random(100);
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					for (int i = 0; i <= 10; i++) {
						int value =  random.nextInt(100);
						cache.write(i + "", value);
						
						if (i == 10) {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							i = -1;
						}
					}					
				}
				
			}
		});
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				
				while (true) {
					for (int i = 0; i <= 10; i++) {
						cache.read(i + "");
						if (i == 10) {
							i = -1;
						}
					}					
				}
				
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					for (int i = 0; i <= 10; i++) {
						cache.read(i + "");
						if (i == 10) {
							i = -1;
						}
					}					
				}
				
			}
		});
		
	} 
	
	private static class Cache {
		private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		private static ReadLock readLock = lock.readLock();
		private static WriteLock writeLock = lock.writeLock();
		private static HashMap<String, Integer> map = new HashMap<String, Integer>();
		 
		public void write(String key, int value) {
			writeLock.lock();
			try {
				map.put(key, value);
				String str = String.format(" write key: %s value: %s", key, value);
				System.out.println(Thread.currentThread().getName() + str);
			} finally {
				writeLock.unlock();
			}
		}
		
		public int read(String key) {
			int value = 0;
			readLock.lock();
			try {
				if (key == null) {
					key = "blank";
				}
				value = map.get(key) == null ? 0 : map.get(key);
				String str = String.format(" read key: %s value: %s", key, value);
				System.out.println(Thread.currentThread().getName() +str);
			} finally {
				readLock.unlock();
			}
			return value;
		}
	}
}
