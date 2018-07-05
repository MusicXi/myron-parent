package com.myron.thread.runnable.demo;

/**
 * 产品类
 * @author Administrator
 *
 */
public class InfoSync {
	private String name;
	private String content;
	
	private boolean isClear=false;//是否被消费了
	
	public InfoSync(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}
	
	
	public synchronized void setInfo(String name, String content) {
		if(!isClear){//未消费,线程等待不能生产
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{//已消费,可以生产
			this.name = name;
			this.content = content;
			isClear=false;		//表示可以取走
			super.notify(); //唤起第一个等待线程执行
		}
	}
	
	public synchronized String getInfo(){
		if(isClear){//未生产,线程等待
			try {
				super.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//已生产,可以消费并唤醒线程
		isClear=true;
		super.notify();
		return this.name+"  =>  "+this.content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
