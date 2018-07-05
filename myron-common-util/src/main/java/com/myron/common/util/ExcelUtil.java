package com.myron.common.util;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * Exceld导入导出工具类
 * @author lin.r.x
 *
 */
public class ExcelUtil {
	private static final Logger logger=LoggerFactory.getLogger(ExcelUtil.class); 
	
	private ExcelUtil() {
		throw new IllegalAccessError("Utility class");
	}
 
	/**
	 * 导出excel表格
	 * @param fieldName 字段名-列名映射 Map
	 * @param fieldData 字段名-值映射     List
	 * @param outputStream
	 */
	public static void export(Map<String, String> fieldName, List<Map<String, Object>> fieldData,ServletOutputStream outputStream){
		//1.创建工作簿对象
		XSSFWorkbook workbook=new XSSFWorkbook();
		//2.创建并选择工作表
		XSSFSheet sheet=workbook.createSheet();
		
		//3.创建列头行
		XSSFRow keyRow = sheet.createRow(0); 		//第一行:字段(username)
		XSSFRow NameRow = sheet.createRow(1); 		//第二行:字段中文名称(用户名)
		Set<String> keys=fieldName.keySet();		//获取所有列字段
		int index=0;
		for(String key: keys){
			
			
			//创建抬头栏单元格
			XSSFCell keyCell = keyRow.createCell(index);
			XSSFCell nameCell = NameRow.createCell(index++);
			//设置单元格 样式
			XSSFFont font = workbook.createFont();   
			// font.setColor(HSSFFont.COLOR_RED);   
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); 
			XSSFCellStyle cellStyle=workbook.createCellStyle();
			cellStyle.setFont(font);
			keyCell.setCellStyle(cellStyle);
			nameCell.setCellStyle(cellStyle);
			
			//设置单元格数据类型
			keyCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			nameCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			
			//填值--设置字段名
			keyCell.setCellValue(key);  
			//填值--获取字段映射的中文名
			nameCell.setCellValue(fieldName.get(key));  
		}
		//3.为工作表写入数据
		writeExcelData(keys,fieldData, sheet);
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally{
			if(workbook !=null){
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}				
			}
		}
		try {
			outputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 导出Excel模板
	 * @param fieldName
	 * @param outputStream
	 */
	public static void exportTemplate(Map<String, String> fieldName,ServletOutputStream outputStream){
		//1.创建工作簿对象
		XSSFWorkbook workbook=new XSSFWorkbook();
		//2.创建并选择工作表
		XSSFSheet sheet=workbook.createSheet();
		
		//3.创建列头行 
		XSSFRow keyRow = sheet.createRow(0); 		//第一行:字段(username)
		XSSFRow NameRow = sheet.createRow(1); 		//第二行:字段中文名称(用户名)
		Set<String> keys=fieldName.keySet();		//获取所有列字段
		int index=0;
		for(String key: keys){
			
			sheet.autoSizeColumn(index, true);//自动列宽
			//创建抬头栏单元格
			XSSFCell keyCell = keyRow.createCell(index);
			XSSFCell nameCell = NameRow.createCell(index++);
			
			//设置单元格 样式
			XSSFFont font = workbook.createFont();   
			// font.setColor(HSSFFont.COLOR_RED);   
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); 
			XSSFCellStyle cellStyle=workbook.createCellStyle();
			cellStyle.setFont(font);
			
			keyCell.setCellStyle(cellStyle);
			nameCell.setCellStyle(cellStyle);
			
			//设置单元格 格式
			keyCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			nameCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			
            //填值
			keyCell.setCellValue(key);  
			nameCell.setCellValue(fieldName.get(key));  
		}
	
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally{
			if(workbook !=null){
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}				
			}
		}
		try {
			outputStream.flush();
		} catch (IOException e) {

			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 从Excel中读取数据
	 * @param file
	 * @param className
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String,Object>> readExcelData(CommonsMultipartFile file) throws IOException{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		XSSFWorkbook wb=new XSSFWorkbook(file.getInputStream());	
		
		int sheetNum = wb.getNumberOfSheets();
		
		for(int i=0; i<sheetNum; i++){
			XSSFSheet sheet=wb.getSheetAt(i);
			String sheetName=wb.getSheetName(i);
			if("Sheet0".equals(sheetName)){
				list=readDataFromSheet(sheet);
			}
		}
		wb.close();	
		return list;
	}

	
	/**
	 * 往excel表格写数据
	 * @param keys 列数据的属性
	 * @param fieldData 
	 * @param sheet 
	 */
	private static void writeExcelData(Set<String> keys, List<Map<String, Object>> fieldData, XSSFSheet sheet){
		
		for(int index=0; index<fieldData.size(); index++){
			//TODO 增加List<T> fieldData 的支持
			Map<String, Object> map=fieldData.get(index);
			int rowIndex=index+2;//rowIndex=2 表示跳过表头行(字段和字段中文表头)
			XSSFRow row=sheet.createRow(rowIndex);
			XSSFCell cell=null;
			int j=0;
			for(String key: keys){
				sheet.autoSizeColumn(j, true);//自动列宽
				cell=null;
				Object obj=map.get(key);
				if(obj instanceof Timestamp){
					Date value=(Date)obj;
					cell=row.createCell(j++);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//设置单元格格式
					cell.setCellValue(value.toString());
				}else{
					
					String value=(obj!=null)? obj.toString() :"null";
					row.createCell(j++).setCellValue(value);
				}
				
				
			}
			
		}
	}
	
	/**
	 * 从sheet页中读取数据
	 * @param sheet
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	private static  List<Map<String,Object>> readDataFromSheet(XSSFSheet sheet) {
		
		//最大行数
		int maxRow=sheet.getLastRowNum();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		//获取首航
		Row headRow=sheet.getRow(0);
		//最大列数
		int maxColumn=headRow.getLastCellNum();
		//第三行读取数据
		for(int i=2; i<=maxRow; i++){
			Map<String, Object> obj=new HashMap<String, Object>();
			//int flag=0;							//错误标记
			Row row=sheet.getRow(i);
			
			//跳过空行
			if(row ==null){
				continue;
			}
			for(int index=0; index<maxColumn; index++){
				obj.put(getValues(headRow,index), getValues(row,index));
			}
			//Object o=BeanToMapUtil.convertMap(className, obj);
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 获取行指定列字符内容，如果内容不为空，则去除两端空格,如果内容为空，则返回null
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	private static String getValues(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		String value = null;
		if (cell != null) {
			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue();
			}else{
				value=cell.toString();
			}
			if (value != null) {
				value = value.trim();
				if ("".equals(value)) {
					value = null;
				}
			}

		}
		return value;
	}
}
