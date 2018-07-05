package com.myron.mq.activemq.queue.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.myron.mq.activemq.queue.service.ConsumerService;

@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {

  @Autowired	
  private JmsTemplate jmsTemplate;

  /**
   * 接受消息
   */
  public void receive(Destination destination) {
    TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
    try {
      System.out.println("ConsumerService <==从队列[" + destination.toString() + "]收到了消息:" + tm.getText());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }


}
