package ${table.projectName}.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myron.db.mybatis.bean.Page;

import ${table.projectName}.bean.${table.className};
import ${table.projectName}.dao.${table.className}Dao;
import ${table.projectName}.service.${table.className}Service;

@Service("${table.className?uncap_first}Service")
public class ${table.className}ServiceImpl  implements ${table.className}Service{
	
	@Autowired
	private ${table.className}Dao ${table.className?uncap_first}Dao;
	
	@Override
	public int create${table.className}(${table.className} ${table.className?uncap_first}) {
		return this.${table.className?uncap_first}Dao.insertSelective(${table.className?uncap_first});
	}
	
	@Override
	public int update${table.className}(${table.className} ${table.className?uncap_first}) {
		return this.${table.className?uncap_first}Dao.updateByPrimaryKeySelective(${table.className?uncap_first});
	}


	@Override
	public int delete${table.className}(String id) {
		return this.${table.className?uncap_first}Dao.deleteByPrimaryKey(id);
	}
	
	@Override
	public ${table.className} find${table.className}ByPrimaryKey(String id) {
		return this.${table.className?uncap_first}Dao.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> findMapListByPage(${table.className} ${table.className?uncap_first}, Page page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("${table.className?uncap_first}", ${table.className?uncap_first});
		map.put("page", page);
		List<Map<String, Object>> list=this.${table.className?uncap_first}Dao.selectMapListByPage(map);
		map.put("data", list);	
		return map;
	}
	
	@Override
	public Map<String, Object> findListByPage(${table.className} ${table.className?uncap_first}, Page page) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("${table.className?uncap_first}", ${table.className?uncap_first});
		map.put("page", page);
		List<${table.className}> list=this.${table.className?uncap_first}Dao.selectListByPage(map);
		map.put("data", list);	
		return map;
	}

	@Override
	public List<Map<String, Object>> findMapList(${table.className} ${table.className?uncap_first}) {
		return this.${table.className?uncap_first}Dao.selectMapList(${table.className?uncap_first});
	}
	
	@Override
	public List<${table.className}> findList(${table.className} ${table.className?uncap_first}){
		return this.${table.className?uncap_first}Dao.selectList(${table.className?uncap_first});
	}






}
