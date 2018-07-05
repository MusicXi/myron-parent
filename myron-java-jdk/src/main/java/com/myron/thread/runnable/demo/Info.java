package com.myron.thread.runnable.demo;

/**
 * 产品类
 * @author Administrator
 *
 */
public class Info {
	private String name;
	private String content;
	
	public Info(String name, String content) {
		super();
		this.name = name;
		this.content = content;
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
