package com.myron.mq.activemq.queue.listener;

/**
 * 1. MessageListenerAdapter可以把一个普通的java当做MessageListener来处理消息。
 * 
 * 2. 返回类型不是void的时候，MessageListenerAdapter会自动把返回值封装成Message，并回复。
 */
public class ConsumerListener {

  public String receiveMessage(String message) {
    System.out.println("ConsumerListener接收到一个Text消息：\t" + message);

    return "I am ConsumerListener response";
  }

}
