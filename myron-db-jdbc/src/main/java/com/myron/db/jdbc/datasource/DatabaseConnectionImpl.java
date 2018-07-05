package com.myron.db.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.myron.db.jdbc.factory.SqlSessionFactory;

public class DatabaseConnectionImpl implements SqlSessionFactory {
	private String driver;
	private String url;
	private String user;
	private String password;
	private Connection connection=null;
	
/*	public static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";
	public static final String DBURL="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	public static final String DBUSER="scott";
	public static final String DBPASSWORD="scott";
	
	public DatabaseConnectionImpl(){ //在构造方法中进行数据库连接
		try {
			Class.forName(DBDRIVER);
			this.conn=DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
*/	
	public DatabaseConnectionImpl(String driver, String url, String user,String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		try {
			Class.forName(this.driver);
			this.connection=DriverManager.getConnection(this.url, this.user, this.password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void close() {//关闭数据库连接
		if(this.connection!=null){
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
