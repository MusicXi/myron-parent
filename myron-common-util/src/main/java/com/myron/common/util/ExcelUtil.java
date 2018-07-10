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
 * Exceld 导入/导出/模板下载 工具类
 *
 * @author lin.r.x
 */
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private ExcelUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 导出excel表格
     *
     * @param fieldName    字段名-列名映射 Map
     * @param fieldData    字段名-值映射     List
     * @param outputStream
     */
    public static void export(String sheetName, Map<String, String> fieldName, List<Map<String, Object>> fieldData, ServletOutputStream outputStream) {
        //1.创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建并选择工作表
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //3.创建列头行
        //第一行:字段(username)
        XSSFRow keyRow = sheet.createRow(0);
        keyRow.setZeroHeight(true);
        //第二行:字段中文名称(用户名)
        XSSFRow NameRow = sheet.createRow(1);
        //获取所有列字段
        Set<String> keys = fieldName.keySet();
        int index = 0;
        for (String key : keys) {


            //创建抬头栏单元格
            XSSFCell keyCell = keyRow.createCell(index);
            XSSFCell nameCell = NameRow.createCell(index++);
            //设置单元格 样式
            XSSFFont font = workbook.createFont();
            // font.setColor(HSSFFont.COLOR_RED);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
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
        writeExcelData(keys, fieldData, sheet);
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } 

    }
    
    /**
     * 导出excel表格
     *
     * @param fieldName    字段名-列名映射 Map
     * @param fieldData    字段名-值映射     List
     * @param outputStream
     */
    public static void export(String sheetName, Map<String, String> fieldName, List<Map<String, Object>> fieldData, ServletOutputStream outputStream, DataHandler dataHandler) {
        //1.创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建并选择工作表
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //3.创建列头行
        XSSFRow keyRow = sheet.createRow(0);        //第一行:字段(username)
        keyRow.setZeroHeight(true);
        XSSFRow NameRow = sheet.createRow(1);        //第二行:字段中文名称(用户名)
        Set<String> keys = fieldName.keySet();        //获取所有列字段
        int index = 0;
        for (String key : keys) {


            //创建抬头栏单元格
            XSSFCell keyCell = keyRow.createCell(index);
            XSSFCell nameCell = NameRow.createCell(index++);
            //设置单元格 样式
            XSSFFont font = workbook.createFont();
            // font.setColor(HSSFFont.COLOR_RED);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
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
        writeExcelData(keys, fieldData, sheet, dataHandler);
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } 

    }

    /**
     * 导出Excel模板
     *
     * @param fieldName
     * @param outputStream
     */
    public static void exportTemplate(Map<String, String> fieldName, ServletOutputStream outputStream) {
        //1.创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建并选择工作表
        XSSFSheet sheet = workbook.createSheet();

        //3.创建列头行
        XSSFRow keyRow = sheet.createRow(0);        //第一行:字段(username)
        XSSFRow NameRow = sheet.createRow(1);        //第二行:字段中文名称(用户名)
        Set<String> keys = fieldName.keySet();        //获取所有列字段
        int index = 0;
        for (String key : keys) {

            sheet.autoSizeColumn(index, true);//自动列宽
            //创建抬头栏单元格
            XSSFCell keyCell = keyRow.createCell(index);
            XSSFCell nameCell = NameRow.createCell(index++);

            //设置单元格 样式
            XSSFFont font = workbook.createFont();
            // font.setColor(HSSFFont.COLOR_RED);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
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
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
 
    }

    /**
     * 从Excel中读取数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> readExcelData(CommonsMultipartFile file) throws IOException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());

        int sheetNum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetNum; i++) {
            XSSFSheet sheet = wb.getSheetAt(i);
            String sheetName = wb.getSheetName(i);
            if ("Sheet0".equals(sheetName)) {
                list = readDataFromSheet(sheet);
            }
        }
        return list;
    }


    /**
     * 往excel表格写数据
     *
     * @param keys      列数据的属性
     * @param fieldDataList
     * @param sheet
     */
    private static void writeExcelData(Set<String> keys, List<Map<String, Object>> fieldDataList, XSSFSheet sheet) {
    	Map<String, Object> fieldData;//行数据
    	XSSFRow row;//行对象
        XSSFCell cell;//单元格对象
    	
    	Object cellData; //单元格数据
        Date date;		//日期
        for (int index = 0; index < fieldDataList.size(); index++) {
            //TODO 增加List<T> fieldData 的支持
            fieldData = fieldDataList.get(index);
            int rowIndex = index + 2;//rowIndex=2 表示跳过表头行(字段和字段中文表头)
            row = sheet.createRow(rowIndex);
            int j = 0;
            for (String key : keys) {
                sheet.autoSizeColumn(j, true);//自动列宽
                cellData = fieldData.get(key);
                if (cellData instanceof Timestamp) {
                	date = (Date) cellData;
                    cell = row.createCell(j++);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//设置单元格格式
                    cell.setCellValue(date.toString());
                } else {

                    String value = (cellData != null) ? cellData.toString() : "null";
                    row.createCell(j++).setCellValue(value);
                }
                cellData = null;
                date = null;
                cell = null;
            }
            row = null;

        }
    }
    
    /**
     * 往excel表格写数据
     *
     * @param keys      列数据的属性
     * @param fieldDataList
     * @param sheet
     */
    private static void writeExcelData(Set<String> keys, List<Map<String, Object>> fieldDataList, XSSFSheet sheet, DataHandler dataHandler) {
    	Map<String, Object> fieldData;//行数据
    	XSSFRow row;//行对象
    	for (int index = 0; index < fieldDataList.size(); index++) {
    		//TODO 增加List<T> fieldData 的支持
    		fieldData = fieldDataList.get(index);
    		int rowIndex = index + 2;//rowIndex=2 表示跳过表头行(字段和字段中文表头)
    		row = sheet.createRow(rowIndex);
    		dataHandler.handle(rowIndex, fieldData, row);
    		
    	}
    }

    /**
     * 从sheet页中读取数据
     *
     * @param sheet
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static List<Map<String, Object>> readDataFromSheet(XSSFSheet sheet) {

        //最大行数
        int maxRow = sheet.getLastRowNum();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //获取首航
        Row headRow = sheet.getRow(0);
        //最大列数
        int maxColumn = headRow.getLastCellNum();
        //第三行读取数据
        for (int i = 2; i <= maxRow; i++) {
            Map<String, Object> obj = new HashMap<String, Object>();
            Row row = sheet.getRow(i);

            //跳过空行
            if (row == null) {
                continue;
            }
            for (int index = 0; index < maxColumn; index++) {
                obj.put(getValues(headRow, index), getValues(row, index));
            }

            list.add(obj);
        }
        return list;
    }

    /**
     * 获取行指定列字符内容，如果内容不为空，则去除两端空格,如果内容为空，则返回null
     *
     * @param row
     * @return
     */
    private static String getValues(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        String value = null;
        if (cell != null) {
            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                value = cell.getStringCellValue();
            } else {
                value = cell.toString();
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

    /**
     * 数据处理接口
     */
    public interface DataHandler {
        void handle(int rowIndex, Map<String, Object> rowData, XSSFRow row);
    }
}
