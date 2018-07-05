package com.myron.common.util;

/**
 * 数据类型转换工具类
 * 
 * @author Administrator
 *
 */
public class DataTypeUtil {

	/**
	 * mysql字段类型 to JdbcTYPE
	 * 
	 * @param mySqlType
	 * @return
	 */
	public static String mySqlToJdbc(String mySqlType) {
		String resultStr = "VARCHAR";
		if (mySqlType.indexOf("char") == 0) {
			resultStr = "CHAR";
		} else if (mySqlType.indexOf("bigint") != -1) {
			resultStr = "BIGINT";
		} else if (mySqlType.indexOf("int") != -1) {
			resultStr = "INTEGER";
		} else if (mySqlType.indexOf("datetime") != -1) {
			resultStr = "TIMESTAMP";
		} else if (mySqlType.indexOf("date") != -1) {
			resultStr = "DATE";
		} else if (mySqlType.indexOf("decimal") != -1) {
			resultStr = "DECIMAL";
		} else if (mySqlType.indexOf("double") != -1) {
			resultStr = "DOUBLE";
		} else if (mySqlType.indexOf("float") != -1) {
			resultStr = "FLOAT";
		}

		return resultStr;
	}

	/**
	 * jdbc类型转java类型
	 * @param jdbcType
	 * @return
	 */
	public static String jdbcTypeToJavaType(String jdbcType) {
		String resultStr = "String";
		if ((jdbcType.equals("CHAR")) || (jdbcType.equals("VARCHAR"))
				|| (jdbcType.equals("LONGVARCHAR"))
				|| (jdbcType.equals("CLOB"))) {
			resultStr = "String";
		} else if (jdbcType.equals("TINYINT")) {
			resultStr = "Byte";
		} else if (jdbcType.equals("SMALLINT")) {
			resultStr = "Short";
		} else if (jdbcType.equals("INTEGER")) {
			resultStr = "Integer";
		} else if (jdbcType.equals("BIGINT")) {
			resultStr = "Long";
		} else if ((jdbcType.equals("DATETIME")) || (jdbcType.equals("DATE"))
				|| (jdbcType.equals("TIME"))) {
			resultStr = "Date";
		} else if (jdbcType.equals("TIMESTAMP")) {
			resultStr = "Date";
		} else if ((jdbcType.equals("BINARY"))
				|| (jdbcType.equals("VARBINARY"))
				|| (jdbcType.equals("LONGVARBINARY"))
				|| (jdbcType.equals("BLOB"))) {
			resultStr = "Byte[]";
		} else if ((jdbcType.equals("DECIMAL")) || (jdbcType.equals("NUMERIC"))) {
			resultStr = "BigDecimal";
		} else if ((jdbcType.equals("FLOAT")) || (jdbcType.equals("DOUBLE"))) {
			resultStr = "Double";
		} else if (jdbcType.equals("REAL")) {
			resultStr = "Float";
		} else if (jdbcType.equals("BIT")) {
			resultStr = "Boolean";
		}
		return resultStr;
	}
}
