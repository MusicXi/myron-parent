package com.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 200000; i++) {
            LOGGER.info("Info [" + i + "]");
            //Thread.sleep(1000);
        }
    }
}