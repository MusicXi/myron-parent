package com.myron.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class DatabaseHealthChecker extends BaseHealthChecker
{
    public DatabaseHealthChecker (CountDownLatch latch)  {
        super("Database Service", latch);
    }
 
    @Override
    public void verifyService()
    {
        System.out.println(Thread.currentThread().getName() + " execute Checking " + this.getServiceName());
        try
        {
            Thread.sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}