package com.myron.thread.runnable.demo;

/**
 * 消费者
 * @author Administrator
 *
 */
public class Consumer implements Runnable{
	//private Info info;
	private InfoSync info;
	
	
	
	public Consumer(InfoSync info) {
		super();
		this.info = info;
	}



	@Override
	public void run() {
		for(int i=0; i<50; i++){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(this.info.getInfo());
			//System.out.println(this.info.getName()+"--->"+this.info.getContent());
		}
		
	}

}
