package com.test.spring.support.configuration;

import org.springframework.stereotype.Component;

@Component("testComponentBean2")
public class TestComponentBean {
	private String username;
	private String url;
	private String password;
	
	

    public void sayHello(){
        System.out.println("TestComponentBean sayHello...");
    }

    public String toString(){
        return "username:"+this.username+",url:"+this.url+",password:"+this.password;
    }

    public void start(){
        System.out.println("TestComponentBean 初始化。。。");
    }

    public void cleanUp(){
        System.out.println("TestBean 销毁。。。");
    }
}