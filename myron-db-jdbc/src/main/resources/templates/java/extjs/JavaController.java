package  ${table.projectName}.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myron.common.util.ExcelUtil;
import com.myron.common.util.JsonUtil;
import com.myron.common.util.StringUtils;
import com.myron.db.mybatis.bean.ExtjsPage;
import ${table.projectName}.bean.${table.className};
import ${table.projectName}.service.${table.className}Service;


@Controller
@RequestMapping("${table.jspContextPath}")
public class ${table.className}Controller {
	private static final Logger logger = LoggerFactory.getLogger(${table.className}Controller.class);
	
	@Autowired
	private ${table.className}Service ${table.className?uncap_first}Service;
	

	@RequestMapping("findListByPage.do")
	@ResponseBody
	public  Map<String, Object> findListByPage(String filter, ${table.className} ${table.className?uncap_first}, ExtjsPage<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		
		if (! StringUtils.isBlank(filter)) {
			${table.className?uncap_first} = JsonUtil.toBean(filter, ${table.className}.class);			
		}
		//设置默认排序属性
		//page.setDefaultSort("createTime", "desc");
		page = (ExtjsPage<Map<String, Object>>)this.${table.className?uncap_first}Service.findMapListByPage(${table.className?uncap_first}, page);
		map.put("total", page.getTotalCount());
		map.put("records", page.getResultList());
		return map;
	}
	
	@RequestMapping("create${table.className}.do")
	@ResponseBody
	public Map<String, Object> create${table.className}(@RequestBody List<${table.className}> ${table.className?uncap_first}List, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.create${table.className}(${table.className?uncap_first}List);
		return map;
	}
	
	@RequestMapping("update${table.className}.do")
	@ResponseBody
	public Map<String, Object> update${table.className}(@RequestBody List<${table.className}> ${table.className?uncap_first}List, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.update${table.className}(${table.className?uncap_first}List);
		return map;
	}
	
	@RequestMapping("delete${table.className}.do")
	@ResponseBody
	public Map<String, Object> delete${table.className}(@RequestBody List<${table.className}> ${table.className?uncap_first}List, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.delete${table.className}(${table.className?uncap_first}List);
		return map;
	}
	
	/**
	 * 导出Excel文件
	 * @param req
	 * @param resp
	 * @param model
	 */
	@RequestMapping("exportExcel.do")
	public void exportExcel(HttpServletRequest req, HttpServletResponse resp, ModelMap model){
		
		resp.setContentType("application/x-msdownload");
		try {
			ServletOutputStream output=resp.getOutputStream();
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = String.valueOf(simpleDateFormat.format(date));
			String fileName = StringUtils.toUtf8String("${table.className}_"+dateStr);
			//String fileName = "Test";
			resp.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".xlsx\"");
			
			List<Map<String,Object>> fieldData = this.${table.className?uncap_first}Service.findMapList(new ${table.className}());
			Map<String,String> fieldName = new LinkedHashMap<String,String>();
			<#list table.columnList as column>
			fieldName.put("${column.camelField}", <#if column.comment!="">"${column.comment}"<#else>"无列名"</#if>);
			</#list>
			ExcelUtil.export(fieldName, fieldData, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * excel导入
	 * TODO 导入成功回调提示
	 * @param req
	 * @param file
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("importExcel.do")
	@ResponseBody
	public Map<String, Object> importExcel(HttpServletRequest req, 
			@RequestParam(value="excelFile", required=false) MultipartFile file) throws Exception{
		logger.debug("执行导入操作开始");
		Map<String, Object> map = new HashMap<>();
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) file;
		try {
			List<Map<String,Object>> list = ExcelUtil.readExcelData(commonsMultipartFile);
			for (Map<String,Object> obj : list) {
				${table.className} ${table.className?uncap_first} = new ${table.className}();
				BeanUtils.populate(${table.className?uncap_first}, obj);
				//TODO 插入主键
				this.${table.className?uncap_first}Service.create${table.className}(${table.className?uncap_first});				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		map.put("tip", "导入成功啦啦啦");
		map.put("success", true);
		return map;
	}

	/**
	 * 下载模板
	 * @param model
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("downloadTemplate.do")
	public void downloadTemplate(String model,HttpServletRequest req, HttpServletResponse resp){
		Map<String, String> fieldName;
		try {
			if (model != null && ! "".equals(model)) {
				ObjectMapper mapper = new ObjectMapper();
				fieldName = mapper.readValue(model, LinkedHashMap.class);								
			} else {
				fieldName = new LinkedHashMap<String,String>();
				<#list table.columnList as column>
				<#if column.key!="PRI">
				fieldName.put("${column.camelField}", <#if column.comment!="">"${column.comment}"<#else>"无列名"</#if>);				
				</#if>
				</#list>
			}
			ServletOutputStream outputStream;
			
			outputStream = resp.getOutputStream();
			String fileName = StringUtils.toUtf8String("${table.className?uncap_first}Template");

			resp.setContentType("application/x-msdownload");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + ".xlsx\"");

			ExcelUtil.exportTemplate(fieldName, outputStream);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	
	
}
