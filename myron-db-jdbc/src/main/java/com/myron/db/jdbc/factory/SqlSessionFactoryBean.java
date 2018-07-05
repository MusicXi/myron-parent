package com.myron.db.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myron.db.jdbc.datasource.MyronDataSource;



/**
 * jdbc 连接实现类
 * @author Administrator
 *
 */
public class SqlSessionFactoryBean implements SqlSessionFactory{
	private static Logger logger=LoggerFactory.getLogger(SqlSessionFactoryBean.class);
	
	private MyronDataSource dataSource;
	private Connection connection;
	
	

	public SqlSessionFactoryBean(MyronDataSource dataSource) {
		super();
		this.dataSource = dataSource;
		try {
			Class.forName(this.dataSource.getDriverClassName());
			this.connection=DriverManager.getConnection(this.dataSource.getUrl(), this.dataSource.getUsername(), this.dataSource.getPassword());
			logger.debug("获取数据库连接");
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
	public void close() {
		if(this.connection!=null){
			try {
				this.connection.close();
				logger.debug("数据库连接关闭");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public MyronDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(MyronDataSource dataSource) {
		this.dataSource = dataSource;

	}
	
	

}
