package com.wisely.highlight.spring4.tashscheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {
	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 每隔五秒执行一次:
	 */
	@Scheduled(fixedRate=5000)
	public void reportCurrentTime(){
		System.out.println("ScheduledTaskService.reportCurrentTime() 每隔五秒执行一次:"+dateFormat.format(new Date()));
	}
	@Scheduled(cron="0 12 10 ? * *")
	public void fixTimeExcetion(){
		System.out.println("ScheduledTaskService.fixTimeExcetion() 指定时间执行"+ dateFormat.format(new Date())+"执行!");
	}
}
