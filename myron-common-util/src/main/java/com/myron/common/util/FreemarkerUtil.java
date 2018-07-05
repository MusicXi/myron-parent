package com.myron.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(FreemarkerUtil.class);
	//创建一个freemarker.template.Configuration实例，它是存储 FreeMarker 应用级设置的核心部分
	private static Configuration configuration=new Configuration(Configuration.VERSION_2_3_22);//指定版本号
	//private static String templatePath;
	
	public FreemarkerUtil(){
		throw new IllegalAccessError("Utility class");
	}
	
    /**
     * 通过模板名称获取模板
     * @param templateName
     * @return
     * @throws IOException
     */
    public static Template getFreemarkTemplate(String templateName) throws IOException{
    	
        //获得模板文件路径
        String templatePath=FreemarkerUtil.class.getClassLoader().getResource("/templates/freemarker").getPath();
        LOGGER.info("FreemarkerUtil模板路径：{}",templatePath);
        //设置模板目录
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        //设置默认编码格式
        configuration.setDefaultEncoding("UTF-8");
        
        //从设置的目录中获得模板
        Template template = configuration.getTemplate(templateName);
        return template;
    }
    
    /**
     * 生成html文件 (将模板+数据生成的结果写入文件中，得到一个静态文件)
     * @param file	生成文件的路径
     * @param template	生成文件的模板
     * @param data	需要渲染到html的数据
     * @throws IOException 
     * @throws TemplateException 
     */
    public static void createHtmlFile(File file, Template template, Map<String, Object> data) throws TemplateException, IOException{
    	//文件输出流写入器
    	Writer writer=new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        //将模板+数据生成的结果写入文件中，得到一个静态文件
    	Writer out = new OutputStreamWriter(System.out);
    	template.process(data, writer);
    	template.process(data, out);
        writer.flush();
        writer.close();
    }
    
    /**
     * 批量生成html文件 (将模板+数据生成的结果写入文件中，得到一个静态文件)
     * @param file	生成文件的路径
     * @param template	生成文件的模板
     * @param list	需要渲染到html的数据列表集合
     * @throws IOException 
     * @throws TemplateException 
     */
    public static void createHtmlFile(File file, Template template, List<Map<String, Object>> list) throws TemplateException, IOException{
    	//文件输出流写入器
    	Writer writer=new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        //将模板+数据生成的结果写入文件中，得到一个静态文件
    	for(Map<String, Object> data: list){
    		template.process(data, writer);    		
    	}
        writer.flush();
        writer.close();
    }
}
