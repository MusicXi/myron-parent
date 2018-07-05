package com.myron.thread.runnable.demo;

/**
 * 生产者 实现Runnable接口
 * @author Administrator
 *
 */
public class Produceser implements Runnable{
	//private Info info;
	private InfoSync info;
	
	
	public Produceser(InfoSync info) {
		super();
		this.info = info;
	}


	@Override
	public void run() {
		boolean flag=false;
		for(int i=0; i<50; i++){//循环生产产品A和B
			
			if(flag){
				//this.info.setName("产品A");
				try {
					Thread.sleep(50);//加入延迟
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//this.info.setContent("产品A的包装");
				this.info.setInfo("产品A", "产品A的包装");
				flag=false;
			}else{
				//this.info.setName("产品B");
				try {
					Thread.sleep(50);//加入延迟
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//this.info.setContent("产品B的包装");
				this.info.setInfo("产品B", "产品B的包装");
				flag=true;
			}
			
		}
	}

}
