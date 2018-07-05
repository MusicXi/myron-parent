package com.myron.db.jdbc.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myron.db.jdbc.bean.Column;
import com.myron.db.jdbc.factory.SqlSessionFactory;


public class MyronJdbcTemplate {
	private static Logger logger=LoggerFactory.getLogger(MyronJdbcTemplate.class);
	
	private SqlSessionFactory sessionFactory;
	
	public List<Column> queryColumn(String tableName){
		List<Column> list=new ArrayList<Column>();
		//查询sql语句
		String sql = "show full columns from ?"; 
		logger.debug("=> excute:{}",sql);
		logger.debug("=> param:{}",tableName);
		sql=sql.replace("?", tableName);
			
		PreparedStatement ps;
		try {
			//执行查询
			ps = (PreparedStatement) sessionFactory.getConnection().prepareStatement(sql);
			ResultSet rs=ps.executeQuery();	
			
			//处理结果集
			while(rs.next()){
				Column column=new Column();
				
//				String field=StringUtils.toCamelCase(rs.getString("FIELD"));
//				String type=DataTypeUtil.mySqlToJdbc(rs.getString("TYPE"));
				String field=rs.getString("FIELD");
				String type=rs.getString("TYPE");
				String collation=rs.getString("COLLATION");
				String isNull=rs.getString("NULL");
				String key=rs.getString("KEY");
				String extra=rs.getString("EXTRA");
				String comment=rs.getString("COMMENT");
				
				column.setField(field);
				column.setType(type);
				column.setCollation(collation);
				column.setIsNull(isNull);
				column.setKey(key);
				column.setExtra(extra);
				column.setComment(comment);
				System.out.println(column);
				list.add(column);
			}
			logger.debug("<= result:{}",list);
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			sessionFactory.close();
		}
		return list;
	}

	public SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
