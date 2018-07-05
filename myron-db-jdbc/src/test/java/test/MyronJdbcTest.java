package test;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.myron.common.util.StringUtils;
import com.myron.db.jdbc.bean.Column;
import com.myron.db.jdbc.bean.Table;
import com.myron.db.jdbc.datasource.MyronDataSource;
import com.myron.db.jdbc.factory.SqlSessionFactory;
import com.myron.db.jdbc.factory.SqlSessionFactoryBean;
import com.myron.db.jdbc.template.MyronJdbcTemplate;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class MyronJdbcTest {
	
	public static void main(String[] args) {
		//提交连接参数
		String url = "jdbc:mysql://127.0.0.1:3306/db_myron_extjs";
		String username = "root";
		String password = "123456";
		String driverClassName="com.mysql.jdbc.Driver";
		
		String tableName="sys_resource";				//选择表名
		String filePath="F:"+File.separator+"temp";		//生成文件的目录
		String[] fileType={"bean","dao","service"};		//生成源代码的类型(取决模板类型)
		String projectName="com.myron.ims";				//生成后端的项目名
		
		//查询数据库表的列信息
		MyronDataSource dataSource=new MyronDataSource(url, username, password, driverClassName);
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBean(dataSource);
		MyronJdbcTemplate jdbcTemplate=new MyronJdbcTemplate();
		jdbcTemplate.setSessionFactory(sessionFactory);
		List<Column> columnList=jdbcTemplate.queryColumn(tableName);
		
		//数据库表信息
		Table table=new Table();
		table.setTableName(tableName);
		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
		table.setColumnList(columnList);
		//table.setFileFormat("java");
		
	
		table.setPackageName("com.myron.ims");
		
		//源文件输出路径
//		String filePath="F:"+File.separator+"temp";
		File parentFile = new File(filePath);
	    if (!parentFile.exists()){
	    	parentFile.mkdirs();
		}
		//定义源文件文件
		File file=new File(filePath+File.separator+table.getClassName()+"."+"java");
	    if (file.exists()) {
	      file.delete();
	    }
	    Writer out=null;
	    try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos, "UTF-8");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//获取freemark 模板
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
		
        //freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java","classpath:templates/extjs");
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
        
        
        // 装载模板
//      String templateName="defaulteTemplate.ftl";
        String templateName="JavaBean.java";
        try {
			Template temp = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
			System.out.println(temp);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("table", table);
			temp.process(map, out);
			Desktop.getDesktop().open(parentFile);
		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
	
		

	}
	

	
	/**
	 * 根据数据源获取tableName表的信息
	 * @param dataSource
	 * @param tableName
	 * @return
	 */
	public static Table getTableInfo(MyronDataSource dataSource,String tableName){
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBean(dataSource);
		MyronJdbcTemplate jdbcTemplate=new MyronJdbcTemplate();
		jdbcTemplate.setSessionFactory(sessionFactory);
		List<Column> columnList=jdbcTemplate.queryColumn(tableName);
		
		//数据库表信息
		Table table=new Table();
		table.setTableName(tableName);
		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
		table.setColumnList(columnList);
		return table;
	}
	
	/**
	 * 根据表信息，指定文件位置，输出fileType类型的源代码
	 */
	public static void export(Table table, String rootPath,String fileType) throws TemplateException, IOException{
		Writer out=null;
		Template template=null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", table);
		
		String filePath=rootPath+packageToPath(table.getPackageName());
		
		template=getTemplate(fileType);
		out=getOutput(table,filePath,fileType);
		template.process(map, out);
	
	}
	
	/**
	 * 根据文件类型获取对应模板
	 * @param fileType
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 */
	public static Template getTemplate(String fileType) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException{
		
		//获取freemark 模板
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
		
        //freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java","classpath:templates/extjs");
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
        
        
        // 装载模板
//		String templateName="defaulteTemplate.ftl";
       String templateName=getTemplateName(fileType);;
        
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);;
		return template;
	}
	
	public static Writer getOutput(Table table,String filePath,String fileType) throws IOException{
		Writer out=null;
		File parentFile = new File(filePath);
	    if (!parentFile.exists()){
	    	parentFile.mkdirs();
		}
	    if("bean".equals(fileType) || "dao".equals(fileType) || "service".equals(fileType)
	    		|| "controller".equals(fileType)){
	    	String fileFormat="java";
	    	//定义源文件文件
			File sourceFile=new File(filePath+File.separator+fileType+File.separator+table.getClassName()+"."+fileFormat);
		    if (sourceFile.exists()) {
		    	sourceFile.delete();
		    }
		    sourceFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(sourceFile);
			out = new OutputStreamWriter(fos, "UTF-8");
	    }
		
		return out;
	}
	
	/**
	 * 包名转文件路径名
	 * @param packageName
	 * @return
	 */
	public static String packageToPath(String packageName){
		String filePath="";
		String[] paths=packageName.split(".");
		for(int i=0; i< paths.length; i++){
			filePath=filePath+File.separator+paths[i];
		}
		System.out.println("包名:"+packageName+"=>"+"文件路径:"+filePath);
		return filePath;
	}
	
	/**
	 * 根据源文件类型 获取对应的模板
	 * @param fileType
	 * @return
	 */
	public static String getTemplateName(String fileType){
		//TODO 文件类型对应模板的映射可通过配置文件定义
		String tempalateName="";
		if("bean".equals(fileType)){
			tempalateName="JavaBean.java";
		}
		return tempalateName;
	}

}
