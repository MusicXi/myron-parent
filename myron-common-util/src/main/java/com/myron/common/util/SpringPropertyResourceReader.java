package com.myron.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 读取spring容器加载的属性配置文件
 * @author linrx
 */
public class SpringPropertyResourceReader implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static AbstractApplicationContext abstractContext;
    private static Properties properties = new Properties();

    private static void init() {
        try {
            // get the names of BeanFactoryPostProcessor
            String[] postProcessorNames = abstractContext.getBeanNamesForType(BeanFactoryPostProcessor.class, true, true);

            for (String ppName : postProcessorNames) {
                // get the specified BeanFactoryPostProcessor
                BeanFactoryPostProcessor beanProcessor =
                        abstractContext.getBean(ppName, BeanFactoryPostProcessor.class);
                // check whether the beanFactoryPostProcessor is
                // instance of the PropertyResourceConfigurer
                // if it is yes then do the process otherwise continue
                if (beanProcessor instanceof PropertyResourceConfigurer) {
                    PropertyResourceConfigurer propertyResourceConfigurer =
                            (PropertyResourceConfigurer) beanProcessor;

                    // get the method mergeProperties
                    // in class PropertiesLoaderSupport
                    Method mergeProperties = PropertiesLoaderSupport.class.
                            getDeclaredMethod("mergeProperties");
                    // get the props
                    mergeProperties.setAccessible(true);
                    Properties props = (Properties) mergeProperties.
                            invoke(propertyResourceConfigurer);

                    // get the method convertProperties
                    // in class PropertyResourceConfigurer
                    Method convertProperties = PropertyResourceConfigurer.class.
                            getDeclaredMethod("convertProperties", Properties.class);
                    // convert properties
                    convertProperties.setAccessible(true);
                    convertProperties.invoke(propertyResourceConfigurer, props);

                    properties.putAll(props);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
        abstractContext = (AbstractApplicationContext) applicationContext;
        init();
    }
}