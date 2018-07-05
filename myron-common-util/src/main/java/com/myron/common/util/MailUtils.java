package com.myron.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;



import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 邮件发送工具类
 * @author lin.rx
 * <p><ul>
 * <li>默认使用： classpath:mail.properties
 * <li>默认freeMarker模板路径：classpath:mailTemplate/defaulteTemplate.ftl
 * </ul>
 *
 */
public class MailUtils {
	private static final Logger logger=LoggerFactory.getLogger(MailUtils.class);
	private static JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();;
	private static FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();;
	//private static String mailProperties;
	//private static String templateLoaderPath;
	
	static { 
		initMailHost();
		initFreemarkTemplate();
	}
	
	/**
	 * 初始化邮件服务器
	 */
	private static  void initMailHost(){
        // 设置邮件服务器
//        javaMailSender.setHost("smtp.163.com");
//        javaMailSender.setUsername("mynote7@163.com");
//        javaMailSender.setPassword("6328665");
//        javaMailSender.setDefaultEncoding("UTF-8");
		PropertiesLoader loader=new PropertiesLoader("classpath:mail.properties");
        javaMailSender.setHost(loader.getProperty("mail.host"));
        javaMailSender.setUsername(loader.getProperty("mail.username"));
        javaMailSender.setPassword(loader.getProperty("mail.password"));
        javaMailSender.setDefaultEncoding(loader.getProperty("mail.defaultEncoding"));

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",loader.getProperty("mail.smtp.auth"));  // 验证用户名和密码
        javaMailSender.setJavaMailProperties(properties);
        logger.info("设置邮件服务器:{} 用户:{}", javaMailSender.getHost(),javaMailSender.getUsername());
	
	}
	
	/**
	 * 初始化FreemarkTemplate模板配置
	 */
	private static void initFreemarkTemplate(){
		//配置freeMarker
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:mailTemplate");
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
        try {
			freeMarkerConfigurer.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发送简单邮件
	 * @param subject 	邮件主题
	 * @param Content 	邮件正文
	 * @param receviers 收件人列表
	 */
	public static void sendTxtMail(String subject, String Content, String[] receviers) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(receviers); // 也可以传一个数组 new String[] {"user1@gmail.com","user2@gmail.com"}
        simpleMailMessage.setFrom(javaMailSender.getUsername());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(Content);
        simpleMailMessage.setSentDate(new Date());

        // 发送邮件
        javaMailSender.send(simpleMailMessage);
        logger.info("Send TxtMail Success! 主题:[{}] 收件人:{}", subject, receviers);	
	}
	
	/**
	 * 发送邮件(富文本/html)
	 * @param subject 		邮件主题
	 * @param HtmlContent 	邮件正文
	 * @param receviers 	收件人列表
	 */
	public static void sendHtmlMail(String subject, String HtmlContent,String[] receviers) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		// multipart模式
        MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setTo(receviers);
			mimeMessageHelper.setFrom(javaMailSender.getUsername());
			mimeMessageHelper.setSubject(subject);
			// true 表示启用html
			mimeMessageHelper.setText(HtmlContent.toString(),true);
			mimeMessageHelper.setSentDate(new Date()); // 发送时间   
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        // 发送邮件
        javaMailSender.send(mimeMessage);
        logger.info("Send HtmlMail Success! 主题:[{}] 收件人:{}", subject, receviers);
	}
	
	/**
	 * 发送邮件(单个附件)
	 * @param subject 		邮件主题
	 * @param content		邮件正文
	 * @param receviers	            收件人列表
	 * @param attachment    单个附件
	 */
	public static void sendAttendedFileMail(String subject, String HtmlContent,
			String[] receviers, File attachment) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		// multipart模式
        MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
			mimeMessageHelper.setTo(receviers);
			mimeMessageHelper.setFrom(javaMailSender.getUsername());
			mimeMessageHelper.setSubject(subject);
			
			String html=getMailTemplate(HtmlContent);   // true 表示启用html
			mimeMessageHelper.setText(html,true);
			mimeMessageHelper.setSentDate(new Date()); // 发送时间   
			
			//添加附件
			if(attachment!=null){
				FileSystemResource file = new FileSystemResource(attachment);
				mimeMessageHelper.addAttachment(file.getFilename(),file);        	
			}else{
				logger.error("没有添加任何附件");
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        
        // 发送邮件
        javaMailSender.send(mimeMessage);
        logger.info("Send AttendedFileMail Success! 主题:[{}] 收件人:{}", subject, receviers);
		
	}
	
	
	/**
	 * 发送邮件(多个附件,使用默认邮件模板)
	 * @param subject 		邮件主题
	 * @param aontent		邮件正文
	 * @param receviers	            收件人列表
	 * @param attachments   附件列表
	 */
	public static void sendAttendedFileMail(String subject, String content,
			String[] receviers, List<File> attachments) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		// 设置邮件信息---multipart模式
        MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
			mimeMessageHelper.setTo(receviers);
			mimeMessageHelper.setFrom(javaMailSender.getUsername());
			mimeMessageHelper.setSubject(subject);
			String html=getMailTemplate(content);
			mimeMessageHelper.setText(html,true);  // true 表示启用html
			// <img src='cid:file'/> 此处将文件内容嵌入邮件页面中   
			// 这里的'cid:file'与addInline('file',rarfile)中的file对应   
			//mimeMessageHelper.addInline( "file" , rarfile); 
			
			//是否存在附件
			if(attachments !=null && attachments.size()>0){
				for(File attachment: attachments){//添加多个附件
					FileSystemResource file = new FileSystemResource(attachment);
					mimeMessageHelper.addAttachment(file.getFilename(),file);     	
				}				
			}else{
				logger.error("没有添加任何附件");
			}
			
			//发送时间   
			mimeMessageHelper.setSentDate(new Date());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
      
        // 发送邮件
        javaMailSender.send(mimeMessage);
        logger.info("Send AttendedFileMail Success! 主题:[{}] 收件人:{}", subject, receviers);
		
	} 
	
	
	
	
	/** 
     * 获取模板并将内容输出到模板 
     *  
     * @param content 
     * @return 
     */  
    private static String getMailTemplate(String content) {  
        String html = "";  
        try {  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("projectName", "综合运维平台");  
            map.put("content", content);  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            map.put("sendTime", sdf.format(new Date()));  
            // 装载模板  
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("defaulteTemplate.ftl");  
            // 加入map到模板中 输出对应变量  
            html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
        return html;  
    }
    
    /**
     * 邮件格式校验
     * @param address
     * @return
     */
    public static Boolean isMailFormat(String address){
		// 电子邮件
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(address);
		boolean isMatched = matcher.matches();
		return isMatched;
    }
    
    public static void main(String[] args){
    	 String[] RECEVIERS={"792331407@qq.com"};	
    	//System.out.println(MailUtils.isMailFormat("ddd.ddd@qq.com"));
    	StringBuilder HtmlContent = new StringBuilder("<html><head></head><body><h1>");
		HtmlContent.append("hello!this is spring mail test。<br/>");
		HtmlContent.append("spring 邮件测试。<br/>");
		HtmlContent.append("</h1></body></html>");

		File attachment=new File("D:"+File.separator+"ZXIMS_LOG"+File.separator+"ztesoft_business.log");
		File attachment2=new File("D:"+File.separator+"ztesoft"+File.separator+"mail.jpg");
		List<File> attachments=new ArrayList<File>();
		attachments.add(attachment);
		attachments.add(attachment2);
	
		MailUtils.sendTxtMail("2测试java邮件-简单文本的发送", "这里是一段简单文本", RECEVIERS);
		MailUtils.sendHtmlMail("2测试java邮件-Html文本的发送", HtmlContent.toString(), RECEVIERS);
		MailUtils.sendAttendedFileMail("2测试java邮件-邮件包含文件的发送", "告警啦!告警啦!!!!!", RECEVIERS, attachment);
		MailUtils.sendAttendedFileMail("2测试java邮件-邮件包含多个文件的发送", "告警 111111111111", RECEVIERS, attachments);	
    }
	
	
}

