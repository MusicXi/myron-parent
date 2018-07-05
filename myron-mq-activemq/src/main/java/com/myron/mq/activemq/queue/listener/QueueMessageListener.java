package com.myron.mq.activemq.queue.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 是最原始的消息监听器(MessageListener)，它是JMS规范中定义的一个接口
 * @author Administrator
 *
 */
public class QueueMessageListener implements MessageListener {

  public void onMessage(Message message) {
    TextMessage tm = (TextMessage) message;
    try {
      System.out.println("QueueMessageListener 监听到文本消息：\t" + tm.getText());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
