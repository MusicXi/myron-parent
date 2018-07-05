package com.myron.thread.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationStartupUtil
{
    //List of service checkers
    private static List<BaseHealthChecker> services;
 
    //This latch will be used to wait on
    private static CountDownLatch countDownLatch;
 
    private ApplicationStartupUtil() {}
 
    private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();
 
    public static ApplicationStartupUtil getInstance()
    {
        return INSTANCE;
    }
 
    public static boolean checkExternalServices() throws InterruptedException 
    {
        //Initialize the latch with number of service checkers
        countDownLatch = new CountDownLatch(3);
 
        //All add checker in lists
        services = new ArrayList<BaseHealthChecker>();
        services.add(new NetworkHealthChecker(countDownLatch));
        services.add(new CacheHealthChecker(countDownLatch));
        services.add(new DatabaseHealthChecker(countDownLatch));
 
  
        //Start service checkers using executor framework
        Executor executor = Executors.newFixedThreadPool(services.size());
        for(final BaseHealthChecker v : services)
        {
            executor.execute(v);
        }
        //Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " before await");
        //Now wait till all services are checked
        countDownLatch.await();//当前 线程等待其他线程完成各自的工作后再执行
        System.out.println(Thread.currentThread().getName() + " after await");
        
        //Services are file and now proceed startup
        for(final BaseHealthChecker v : services)
        {
            if( ! v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}