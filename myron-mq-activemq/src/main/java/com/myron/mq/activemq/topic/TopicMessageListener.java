package com.myron.mq.activemq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 和队列监听的代码一样。
 */
public class TopicMessageListener implements MessageListener {

  public void onMessage(Message message) {
    TextMessage tm = (TextMessage) message;
    try {
      System.out.println("TopicMessageListener 监听到消息：\t" + tm.getText());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
