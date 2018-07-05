package com.myron.db.jdbc.datasource;

import com.myron.db.jdbc.factory.SqlSessionFactory;


public class DatabaseConnectionFactory {
	
	public static SqlSessionFactory getDatabaseConnectionInstance(String driver, String url, String user,String password){
		return new DatabaseConnectionImpl(driver,url,user,password);
	}
}
