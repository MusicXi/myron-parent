package com.myron.thread.runnable.demo;

public class InfoTest {
	public static void main(String[] args){
		//Info info=new Info("产品名称","产品的包装");
		InfoSync info=new InfoSync("产品名称","产品的包装");
		Produceser p=new Produceser(info);
		Consumer c=new Consumer(info);
		new Thread(p,"生产线程").start();
		new Thread(c,"消费线程").start();
	}
}
