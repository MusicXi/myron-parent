package ${table.projectName}.service;

import java.util.List;
import java.util.Map;
import com.myron.db.mybatis.bean.Page;

import ${table.projectName}.bean.${table.className};

public interface ${table.className}Service {
	//增删改
	Map<String, Object> create${table.className}(${table.className} ${table.className?uncap_first}) throws Exception;
	Map<String, Object> create${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception;
	
	Map<String, Object> update${table.className}(${table.className} ${table.className?uncap_first}) throws Exception;
	Map<String, Object> update${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception;
	
	Map<String, Object> delete${table.className}(${table.className} ${table.className?uncap_first}) throws Exception;
	Map<String, Object> delete${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception;
	
	//查询
	${table.className} find${table.className}ByPrimaryKey(String id);
	Page<${table.className}> findListByPage(${table.className} ${table.className?uncap_first}, Page<${table.className}> page);
	Page<Map<String, Object>> findMapListByPage(${table.className} ${table.className?uncap_first}, Page<Map<String, Object>> page);
	List<Map<String, Object>> findMapList(${table.className} ${table.className?uncap_first});
	List<${table.className}> findList(${table.className} ${table.className?uncap_first});
	

	
}
