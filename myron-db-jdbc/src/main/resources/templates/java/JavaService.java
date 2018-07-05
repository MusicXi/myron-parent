package ${table.projectName}.service;

import java.util.List;
import java.util.Map;
import com.myron.db.mybatis.bean.Page;

import ${table.projectName}.bean.${table.className};

public interface ${table.className}Service {
	//增删改
	public int create${table.className}(${table.className} ${table.className?uncap_first});
	public int update${table.className}(${table.className} ${table.className?uncap_first});
	
	public int delete${table.className}(String id);
	
	//查询
	public ${table.className} find${table.className}ByPrimaryKey(String id);
	public Map<String, Object> findListByPage(${table.className} ${table.className?uncap_first}, Page page);
	public Map<String, Object> findMapListByPage(${table.className} ${table.className?uncap_first}, Page page);
	public List<Map<String, Object>> findMapList(${table.className} ${table.className?uncap_first});
	public List<${table.className}> findList(${table.className} ${table.className?uncap_first});
	

	
}
