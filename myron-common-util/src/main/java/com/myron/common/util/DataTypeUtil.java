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
		if (("CHAR".equals(jdbcType)) || ("VARCHAR".equals(jdbcType))
				|| ("LONGVARCHAR".equals(jdbcType))
				|| ("CLOB".equals(jdbcType))) {
			resultStr = "String";
		} else if ("TINYINT".equals(jdbcType)) {
			resultStr = "Byte";
		} else if ("SMALLINT".equals(jdbcType)) {
			resultStr = "Short";
		} else if ("INTEGER".equals(jdbcType)) {
			resultStr = "Integer";
		} else if ("BIGINT".equals(jdbcType)) {
			resultStr = "Long";
		} else if (("DATETIME".equals(jdbcType)) || ("DATE".equals(jdbcType))
				|| ("TIME".equals(jdbcType))) {
			resultStr = "Date";
		} else if ("TIMESTAMP".equals(jdbcType)) {
			resultStr = "Date";
		} else if (("BINARY".equals(jdbcType))
				|| ("VARBINARY".equals(jdbcType))
				|| ("LONGVARBINARY".equals(jdbcType))
				|| ("BLOB".equals(jdbcType))) {
			resultStr = "Byte[]";
		} else if (("DECIMAL".equals(jdbcType)) || ("NUMERIC".equals(jdbcType))) {
			resultStr = "BigDecimal";
		} else if (("FLOAT".equals(jdbcType)) || ("DOUBLE".equals(jdbcType))) {
			resultStr = "Double";
		} else if ("REAL".equals(jdbcType)) {
			resultStr = "Float";
		} else if ("BIT".equals(jdbcType)) {
			resultStr = "Boolean";
		}
		return resultStr;
	}
}
