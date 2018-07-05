package test;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myron.common.util.DataTypeUtil;
import com.myron.db.jdbc.bean.Column;




/**
 * 测试获取表的所有字段信息
 * @author Administrator
 *
 */
public class JdbcTest {
	public static void main(String[] args) {
		String url = "jdbc:mysql://127.0.0.1:3306/db_myron_extjs";
		String user = "root";
		String password = "123456";
		String driver="com.mysql.jdbc.Driver";
		

		String tableName = "sys_resource";
		String sql = "show full columns from " + tableName;
		Connection connection=null;

	
		try {
			Class.forName(driver);
			connection= DriverManager.getConnection(url, user, password);
			
			PreparedStatement ps=(PreparedStatement) connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			List<Column> list=new ArrayList<Column>();
 			while(rs.next()){
 				Column column=new Column();
 				
 				String field=rs.getString("FIELD");
 				String type=DataTypeUtil.mySqlToJdbc(rs.getString("TYPE"));
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
 				list.add(column);
 				System.out.println(column);
		
		/*		System.out.print(rs.getString("TYPE")+" ");
				System.out.print(rs.getString("COLLATION")+" ");
				System.out.print(rs.getString("NULL")+" ");
				System.out.print(rs.getString("KEY")+" ");
				System.out.print(rs.getString("EXTRA")+" ");
				System.out.println(rs.getString("COMMENT")+" ");*/
			}
 			
 			System.out.println(list.size());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(connection !=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
