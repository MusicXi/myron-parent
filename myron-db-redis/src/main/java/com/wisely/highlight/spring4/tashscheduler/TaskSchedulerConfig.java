package com.wisely.highlight.spring4.tashscheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.wisely.highlight.spring4.tashscheduler")
@EnableScheduling//开启对任务计划的支持
public class TaskSchedulerConfig {
/*	@Bean
	public TaskScheduler taskScheduler(){
		return new TaskScheduler();
		
	}*/
}
