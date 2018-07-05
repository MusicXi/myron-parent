package com.myron.db.jdbc.factory;

import java.sql.Connection;

public interface SqlSessionFactory {
	/**
	 * 取得数据库连接
	 * @return 返回数据库连接
	 */
	public Connection getConnection(); 
	/**
	 * 关闭数据连接
	 */
	public void close();
}
