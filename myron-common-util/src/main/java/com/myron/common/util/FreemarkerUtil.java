package com.myron.common.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FreemarkerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerUtil.class);

    public FreemarkerUtil() {
        throw new IllegalAccessError("Utility class");
    }


    /**
     * 通过模板名称获取模板
     * @param templateLoaderPath 模板加载目录,例:"classpath:templates/freemarker"
     * @param templateName 模板名称
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static Template getFreemarkTemplate(String templateLoaderPath, String templateName) throws IOException, TemplateException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();

//       freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java/mybatis","classpath:templates/mybatis");
        freeMarkerConfigurer.setTemplateLoaderPaths(templateLoaderPath);
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
        freeMarkerConfigurer.afterPropertiesSet();

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        return template;
    }

    /**
     * 生成html文件 (将模板+数据生成的结果写入文件中，得到一个静态文件)
     *
     * @param file     生成文件的路径
     * @param template 生成文件的模板
     * @param data     需要渲染到html的数据
     * @throws IOException
     * @throws TemplateException
     */
    public static void createHtmlFile(File file, Template template, Map<String, Object> data) throws TemplateException, IOException {
        //文件输出流写入器
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        //将模板+数据生成的结果写入文件中，得到一个静态文件
        Writer out = new OutputStreamWriter(System.out);
        template.process(data, writer);
        template.process(data, out);
        writer.flush();
        writer.close();
    }

    /**
     * 批量生成html文件 (将模板+数据生成的结果写入文件中，得到一个静态文件)
     *
     * @param file     生成文件的路径
     * @param template 生成文件的模板
     * @param list     需要渲染到html的数据列表集合
     * @throws IOException
     * @throws TemplateException
     */
    public static void createHtmlFile(File file, Template template, List<Map<String, Object>> list) throws TemplateException, IOException {
        //文件输出流写入器
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        //将模板+数据生成的结果写入文件中，得到一个静态文件
        for (Map<String, Object> data : list) {
            template.process(data, writer);
        }
        writer.flush();
        writer.close();
    }
}
