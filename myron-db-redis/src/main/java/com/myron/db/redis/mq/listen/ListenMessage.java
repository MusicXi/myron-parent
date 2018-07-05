package com.myron.db.redis.mq.listen;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;



@Component(value="messageDelegateListener")
public class ListenMessage {
	/**
     * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
     */
    private CountDownLatch latch;
    
	public void handleMessage(Serializable message) throws InterruptedException{
		Random ram=new Random();
		int time=ram.nextInt(300);
		//Thread.sleep(time*1000);
		//SmsMessageVo msg=(SmsMessageVo) message;
		System.out.println("耗时："+time+"秒;监听到消息,内容:"+message);
		//latch.countDown();
	
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
	
	
}