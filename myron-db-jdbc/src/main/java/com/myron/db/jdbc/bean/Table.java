package com.myron.db.jdbc.bean;

import java.util.List;

/**
 * 描述类的元数据及额外属性 在模板类中使用${}
 * @author Administrator
 *
 */
public class Table {
	//基本属性
	private String tableName;	//数据库表名
	private String className;	//类名
	private String packageName;	//类所在包名:com.myron.ims.bean
	private String projectName; //类所在项目名:com.myron.ims
	private List<Column> columnList;//属性列表
	
	//额外描述信息
	//private String fileFormat;  //文件格式：java/xml
	private String jspContextPath;//当前对象生成的页面的容器路径或请求路径
	
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
	
	

	public String getJspContextPath() {
		return jspContextPath;
	}

	public void setJspContextPath(String jspContextPath) {
		this.jspContextPath = jspContextPath;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", className=" + className
				+ ", packageName=" + packageName + ", projectName="
				+ projectName + ", columnList=" + columnList + "]";
	}



	
}
