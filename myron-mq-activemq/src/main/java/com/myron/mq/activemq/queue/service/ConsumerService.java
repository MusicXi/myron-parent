package com.myron.mq.activemq.queue.service;

import javax.jms.Destination;

public interface ConsumerService {
  public void receive(Destination queueDestination);
}
