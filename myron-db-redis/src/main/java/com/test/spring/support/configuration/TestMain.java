package com.test.spring.support.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
	
    public static void main(String[] args) {
    	//如果加载spring-context.xml文件：
    	//ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        //@Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
 
        //获取bean
        TestBean tb = (TestBean) context.getBean("testBean2");
        tb.sayHello();
        
        TestComponentBean tcb=(TestComponentBean) context.getBean("testComponentBean2");
        tcb.sayHello();
        context.close();
    }
}