package com.myron.db.jdbc.generator;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class DfosGenerator {
	private static Logger logger=LoggerFactory.getLogger(DfosGenerator.class);
	//后端源代码类型
	private static final String BEAN="bean";
	private static final String DAO="dao";
	private static final String SERVICE="service";
	private static final String SERVICE_IMPL="service.impl";
	private static final String CONTROLLER="controller";
	private static final String MAPPER_XML="dao.mapper";
	
	//前端源代码类型
	private static final String EXT_JSP="jsp";
	private static final String EXT_APP="application";
	private static final String EXT_CONTROLLER="app.controller";
	private static final String EXT_MODEL="app.model";
	private static final String EXT_STORE="app.store";
	private static final String EXT_VIEW_PANEL="app.view.panel";
	private static final String EXT_VIEW_WIN="app.view.win";
	private static final String EXT_VIEW_WIN_IMPORT="app.view.win.import";
	

	public static void main(String[] args) {
		//提交连接参数
		String url = "jdbc:mysql://10.45.44.251:8066/dfos?characterEncoding=utf8&?autoReconnect=true&?zeroDateTimeBehavior=convertToNull";
		String username = "dfos";
		String password = "dfos";
		String driverClassName="com.mysql.jdbc.Driver";
		
		String tableName="c_goods";				//选择表名
		String filePath="F:"+File.separator+"temp";		//生成文件的目录
		String[] codeTypes={BEAN,MAPPER_XML,DAO,SERVICE,SERVICE_IMPL,CONTROLLER};		//生成源代码的类型(取决模板类型)
		String[] frontCodeTypes={EXT_JSP, EXT_APP, EXT_CONTROLLER, EXT_MODEL, EXT_STORE, EXT_VIEW_PANEL,EXT_VIEW_WIN,EXT_VIEW_WIN_IMPORT};
		String projectName="com.ztesoft.dfos";				//生成后端的项目名
		
		logger.info("连接数据库:{} driverClassName:{}",url,driverClassName);
		logger.info("用户:{}",username);
		logger.info("密码:{}",password);
		
		//查询数据库表的列信息
		MyronDataSource dataSource=new MyronDataSource(url, username, password, driverClassName);
		Table table=getTableInfo(dataSource,tableName);
		table.setProjectName(projectName);
		table.setJspContextPath("/product/system/"+StringUtils.uncapitalize(table.getClassName())+"Manager");
		logger.info("获取元数据:{}",table);
		
		
		logger.info("开始生成'{}'的后端{}源代码",table.getClassName(), Arrays.toString(codeTypes));
		for(String type: codeTypes){
			table.setPackageName(projectName+"."+type);
			try {
				exportJavaSourceCode(table,filePath,type);
				logger.info("生成'{}'", type);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("完成'{}'源代码",table.getClassName());
		
//		logger.info("开始生成'{}'的前端{}源代码",table.getClassName(), Arrays.toString(frontCodeTypes));
//		for(String type: frontCodeTypes){
//			table.setPackageName(projectName+"."+type);
//			try {
//				exportFrontSourceCode(table,filePath,type);
//				logger.info("生成'{}'", type);
//			} catch (TemplateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		logger.info("完成'{}'源代码",table.getClassName());
	}
	
	/**
	 * 根据数据源获取tableName表的信息
	 * @param dataSource
	 * @param tableName
	 * @return
	 */
	private static Table getTableInfo(MyronDataSource dataSource,String tableName){
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBean(dataSource);
		MyronJdbcTemplate jdbcTemplate=new MyronJdbcTemplate();
		jdbcTemplate.setSessionFactory(sessionFactory);
		List<Column> columnList=jdbcTemplate.queryColumn(tableName);
		
		//数据库表信息
		Table table=new Table();
		table.setTableName(tableName);
		
		int i=tableName.indexOf("_");
		tableName=tableName.substring(i+1);
		System.out.println(tableName);
		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
		table.setColumnList(columnList);
		return table;
	}
	
	/**
	 * 输出fileType类型的java源代码
	 * @param table 根据表信息
	 * @param rootPath 文件输出根路径
	 * @param codeType 源代码类型
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void exportJavaSourceCode(Table table, String rootPath,String codeType) throws TemplateException, IOException{
		//生成源代码文件目录
		String path=rootPath+javaPackageToPath(table,codeType);
		File filePath = new File(path);
	    if (!filePath.exists()){
	    	filePath.mkdirs();
		}
		logger.info("SourceCode Location:{}", filePath.getPath());
		
		//创建源文件
		File sourceFile =createJavaSourceFile(table,filePath,codeType);
		FileOutputStream fos = new FileOutputStream(sourceFile);
		Writer out = new OutputStreamWriter(fos, "UTF-8");
		
		//根据源代码类型获取模板
		 String templateName=getTemplateName(codeType);
		Template template=getTemplate(templateName);
		logger.debug("'{}'类型源代码 使用模板:{}",codeType,template.getName());
		
		//根据模板及"元数据"往源文件中输入源代码
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", table);
		template.process(map, out);
		
		//弹出生成的源代码文件夹
		Desktop.getDesktop().open(filePath);
	}
	
	
	/**
	 * 生成前端源js/jsp代码
	 * @param table
	 * @param rootPath
	 * @param codeType
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void exportFrontSourceCode(Table table, String rootPath,String codeType) throws TemplateException, IOException{
		//生成源代码文件目录
		String path=rootPath+jspContextPathToPath(table.getJspContextPath(), codeType);
		File filePath = new File(path);
	    if (!filePath.exists()){
	    	filePath.mkdirs();
		}
		logger.info("SourceCode Location:{}", filePath.getPath());
		
		//创建源文件
		File sourceFile =createFrontSourceFile(table.getClassName(),filePath,codeType);
		FileOutputStream fos = new FileOutputStream(sourceFile);
		Writer out = new OutputStreamWriter(fos, "UTF-8");
		
		//根据源代码类型获取模板
		 String templateName=getTemplateName(codeType);
		Template template=getTemplate(templateName);
		logger.debug("'{}'类型源代码 使用模板:{}",codeType,template.getName());
		
		//根据模板及"元数据"往源文件中输入源代码
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", table);
		template.process(map, out);
		
		//弹出生成的源代码文件夹
		Desktop.getDesktop().open(filePath);
	}
	
	/**
	 * 根据文件类型获取对应模板
	 * @param fileType
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 * @throws TemplateException 
	 */
	private static Template getTemplate(String templateName) throws IOException, TemplateException {
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
		
        //freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java","classpath:templates/extjs");
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
		freeMarkerConfigurer.afterPropertiesSet();
	
        //String templateName="defaulteTemplate.ftl";// 装载模板
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
		return template;
	}
	
	/**
	 *  创建源代码文件
	 * @param table 对象名
	 * @param filePath 文件路径
	 * @param fileType 类型
	 * @return
	 * @throws IOException
	 */
	private static File createJavaSourceFile(Table table,File filePath,String fileType) throws IOException{
		File sourceFile=null;
		String fileName="undefined";
		StringBuffer fileformat=new StringBuffer();

	    if(BEAN.equals(fileType)){
	    	fileformat.append("java");
	    	fileName=table.getClassName();
	    }else if(DAO.equals(fileType) || SERVICE.equals(fileType)|| CONTROLLER.equals(fileType)){
	    	fileformat.append("java");
	    	fileName=table.getClassName()+StringUtils.toCapitalizeCamelCase(fileType);
	    }else if(SERVICE_IMPL.equals(fileType)){
	    	fileformat.append("java");
	    	fileType=fileType.replace(".", "_");//"service.impl"=>service_impl
	    	fileName=table.getClassName()+StringUtils.toCapitalizeCamelCase(fileType);
	    }else if(MAPPER_XML.equals(fileType)){
	    	fileformat.append("xml");
	    	fileName=table.getClassName()+"Mapper";
	    }else{
	    	fileformat.append("undefined");
	    }
	    sourceFile=new File(filePath.getPath()+File.separator+fileName+"."+fileformat.toString());
	    if (sourceFile.exists()) {
	    	sourceFile.delete();
	    }
	    sourceFile.createNewFile();
	    logger.info("Create source file:{}",sourceFile);
	    
		return sourceFile;
	}
	
	/**
	 *  创建前端源代码文件
	 * @param table 对象名
	 * @param filePath 文件路径
	 * @param fileType 类型
	 * @return
	 * @throws IOException
	 */
	private static File createFrontSourceFile(String className,File filePath,String fileType) throws IOException{
		File sourceFile=null;
		String fileName="undefined";
		StringBuffer fileformat=new StringBuffer();

	    if(EXT_JSP.equals(fileType)){
	    	fileformat.append("jsp");
	    	fileName="index";
	    }else if(EXT_APP.equals(fileType)){
	    	fileformat.append("js");
	    	fileName="Main";
	    }else if(EXT_CONTROLLER.equals(fileType)){
	    	fileformat.append("js");
	    	fileName=className+"Controller";
	    }else if(EXT_MODEL.equals(fileType) || EXT_STORE.equals(fileType)){
	    	fileformat.append("js");
	    	fileName=className;
	    }else if(EXT_VIEW_PANEL.equals(fileType)){
	    	fileformat.append("js");
	    	fileName=className+"Panel";
	    }else if(EXT_VIEW_WIN.equals(fileType)){
	    	fileformat.append("js");
	    	fileName=className+"Win";
	    }else if(EXT_VIEW_WIN_IMPORT.equals(fileType)){
	    	fileformat.append("js");
	    	fileName="ImportWin";
	    }else{
	    	fileformat.append("undefined");
	    }
	    sourceFile=new File(filePath.getPath()+File.separator+fileName+"."+fileformat.toString());
	    if (sourceFile.exists()) {
	    	sourceFile.delete();
	    }
	    sourceFile.createNewFile();
	    logger.info("Create source file:{}",sourceFile);
	    
		return sourceFile;
	}
	
	/**
	 * java包名 转换 为文件路径名
	 * @param packageName 对象包名
	 * @param fileType	     对象生成源代码文件类型	
	 * @return
	 */
	private static String javaPackageToPath(Table table,String fileType){
		// 如果java类型,添加src目录及子包目录
		if (BEAN.equals(fileType) || DAO.equals(fileType) || MAPPER_XML.equals(fileType)
				|| SERVICE.equals(fileType) || SERVICE_IMPL.equals(fileType) || CONTROLLER.equals(fileType)) {

			String filePath="";
			String[] paths=table.getPackageName().split("\\.");
			for(int i=0; i< paths.length; i++){
				filePath=filePath+File.separator+paths[i];
			}
			filePath=File.separator+"src"+filePath;
			return filePath;
		}
		
		return "";
		
	}
	
	/**
	 * jsp相对路径转 转绝对路径
	 * @param jspContextPath
	 * @param fileType
	 * @return
	 */
	private static String jspContextPathToPath(String jspContextPath,String fileType){
		String filePath="";
		if(EXT_JSP.equals(fileType) || EXT_APP.equals(fileType)){
			filePath=jspContextPath;
		}else if(EXT_CONTROLLER.equals(fileType) ){
			filePath=jspContextPath+File.separator+"app"+File.separator+"controller";
		}else if(EXT_MODEL.equals(fileType)){
			filePath=jspContextPath+File.separator+"app"+File.separator+"model";
		}else if(EXT_STORE.equals(fileType)){
			filePath=jspContextPath+File.separator+"app"+File.separator+"store";			
		}else if(EXT_VIEW_PANEL.equals(fileType)){
			filePath=jspContextPath+File.separator+"app"+File.separator+"view";	
		}else if(EXT_VIEW_WIN.equals(fileType)){
			filePath=jspContextPath+File.separator+"app"+File.separator+"view";	
		}else if(EXT_VIEW_WIN_IMPORT.equals(fileType)){
			filePath=jspContextPath+File.separator+"app"+File.separator+"view";	
		}
		filePath=File.separator+"webapp"+filePath;
		return filePath;
	}
	
	/**
	 * 根据源文件类型 获取对应的模板
	 * @param fileType
	 * @return
	 */
	private static String getTemplateName(String fileType){
		//TODO 文件类型对应模板的映射可通过配置文件定义
		String templateName="";
		if(BEAN.equals(fileType)){
			templateName="JavaBean.java";
		}else if(DAO.equals(fileType)){
			templateName="JavaDao.java";
		}else if(MAPPER_XML.equals(fileType)){
			templateName="JavaMapper.xml";
		}else if(SERVICE.equals(fileType)){
			templateName="JavaService.java";
		}else if(SERVICE_IMPL.equals(fileType)){
			templateName="JavaServiceImpl.java";
		}else if(CONTROLLER.equals(fileType)){
			templateName="JavaController.java";
		}else{
			templateName="undefined";
		}
		
		//后端
		if(EXT_JSP.endsWith(fileType)){
			templateName="ExtjsJsp.jsp";
		}else if(EXT_APP.equals(fileType)){
			templateName="ExtjsApp.js";
		}else if(EXT_CONTROLLER.equals(fileType)){
			templateName="ExtjsController.js";
		}else if(EXT_MODEL.equals(fileType)){
			templateName="ExtjsModel.js";
		}else if(EXT_STORE.equals(fileType)){
			templateName="ExtjsStore.js";
		}else if(EXT_VIEW_PANEL.equals(fileType)){
			templateName="ExtjsPanel.js";
		}else if(EXT_VIEW_WIN.equals(fileType)){
			templateName="ExtjsWin.js";
		}else if(EXT_VIEW_WIN_IMPORT.equals(fileType)){
			templateName="ExtjsImportWin.js";
		}
		return templateName;
	}


}
