package com.wisely.highlight.spring4.tashscheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(TaskSchedulerConfig.class);
		//context.close();
	}

}
