package com.test.spring.support.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 注： 
(1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 

(2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域； 

(3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描。
 * @author Administrator
 *
 */
@Configuration
//添加自动扫描注解，basePackages为TestBean包路径
@ComponentScan(basePackages = "com.test.spring.support.configuration")
///@EnableAspectJAutoProxy //开启Spring 对AspectJ代理的支持
public class TestConfiguration {
	private static final Logger logger=LoggerFactory.getLogger(TestConfiguration.class);
    public TestConfiguration(){
    	logger.info("spring容器启动初始化。。。");
       // System.out.println("spring容器启动初始化。。。");
    }
    
    //@Bean注解注册bean,同时可以指定初始化和销毁方法
    //@Bean(name="testNean",initMethod="start",destroyMethod="cleanUp")
    @Bean(name="testBean2")
    //@Scope("prototype")
    public TestBean testBean() {
        return new TestBean();
    }
}