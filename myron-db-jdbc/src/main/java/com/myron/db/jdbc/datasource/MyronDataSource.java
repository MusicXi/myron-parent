package com.myron.db.jdbc.datasource;

/**
 * 自定义jdbc数据源
 * @author Administrator
 *
 */
public class MyronDataSource {
	private String url = "jdbc:mysql://127.0.0.1:3306/db_myron_extjs";
	private String username = "root";
	private String password = "123456";
	private String driverClassName="com.mysql.jdbc.Driver";
	
	
	
	public MyronDataSource(String url, String username, String password,
			String driverClassName) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	
}
