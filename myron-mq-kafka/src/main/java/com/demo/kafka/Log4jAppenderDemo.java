package com.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jAppenderDemo {
public static final Logger LOG =   LoggerFactory.getLogger(Log4jAppenderDemo.class.getName());
	public static void main(String[] args) {
	    LOG.info("test message:1");
	    LOG.info("test message:2");
	    LOG.info("test message:2");
	    LOG.info("test message:2");
	    LOG.info("test message:2");
	}
}