package com.myron.common.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.*;

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

    /**
     * 根据文件类型获取对应模板
     *  freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java/mybatis","classpath:templates/mybatis");
     * @param templateName
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static  Template getTemplate(String templateName, String[] templateLoaderPaths) throws IOException, TemplateException {
        FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths(templateLoaderPaths);
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
        freeMarkerConfigurer.afterPropertiesSet();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        return template;
    }
    /**
     * 执行动态模版
     *
     * @param templateName * 模版名称
     * @param templateContent * 模版内容
     * @param encoding * 编码格式
     * @param data
     * 数据集
     * @return * @throws Exception
     */
    public static String executeFreeMarkerFromStringTemplate(String templateName, String templateContent, String encoding, Map<String, Object> data) throws Exception {
        String result = "";
        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            if (data == null) {
                data = new HashMap<String, Object>();
            }
            data.put("DateMethodUtil", new DateMethod());
            out = new StringWriter(2048);
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate(templateName, templateContent);
            cfg.setTemplateLoader(stringLoader);
            Template temp = cfg.getTemplate(templateName, encoding);
            temp.process(data, out);
            out.flush();
            result = out.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();
        }
        return result;
    }

    /**
     * 日期运算函数 *
     */
    public static class DateMethod implements TemplateMethodModelEx {
        @Override
        @SuppressWarnings("rawtypes")
        public Object exec(List arguments) throws TemplateModelException {
            Date date = ((SimpleDate) arguments.get(0)).getAsDate();
            String type = arguments.get(1).toString();
            Integer num = ((SimpleNumber) arguments.get(2)).getAsNumber().intValue();
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(date);
            if ("d".equalsIgnoreCase(type)) {
                int i = localCalendar.get(Calendar.DATE);
                localCalendar.set(Calendar.DATE, i + num);
            } else if ("m".equalsIgnoreCase(type)) {
                int i = localCalendar.get(Calendar.MONTH);
                localCalendar.set(Calendar.MONTH, i + num);
            } else if ("y".equalsIgnoreCase(type)) {
                int i = localCalendar.get(Calendar.YEAR);
                localCalendar.set(Calendar.YEAR, i + num);
            }
            return localCalendar.getTime();
        }
    }
}
