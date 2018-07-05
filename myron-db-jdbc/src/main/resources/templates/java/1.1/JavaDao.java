package ${table.packageName};

import java.util.List;
import java.util.Map;

import com.myron.db.mybatis.annotation.MyBatisRepository;


import ${table.projectName}.bean.${table.className};

@MyBatisRepository
public interface ${table.className}Dao {
	//增加记录
	int insert(${table.className} ${table.className?uncap_first});
	int insertSelective(${table.className} ${table.className?uncap_first});
	int insertByBatch(List<${table.className}> list);
	int insertSelectiveByBatch(List<${table.className}> list);
	//删除记录
	int deleteByPrimaryKey(String id);
	
	//修改记录
	int updateByPrimaryKey(${table.className} ${table.className?uncap_first});
	int updateByPrimaryKeySelective(${table.className} ${table.className?uncap_first});
	int updateByBatch(List<${table.className}> list);
	int updateSelectiveByBatch(List<${table.className}> list);
	
	//查询记录
	${table.className} selectByPrimaryKey(String id);
	
	//查询记录列表
	List<${table.className}> selectList(${table.className} ${table.className?uncap_first});
	List<${table.className}> selectListByPage(Map<String, Object> map);	
	
	List<Map<String, Object>> selectMapList(${table.className} ${table.className?uncap_first});
	List<Map<String, Object>> selectMapListByPage(Map<String, Object> map);

}
