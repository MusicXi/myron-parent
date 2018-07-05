package ${table.packageName};

import com.myron.db.mybatis.annotation.MyBatisRepository;
import com.myron.db.mybatis.dao.BaseMybatisDao;
import ${table.projectName}.bean.${table.className};

@MyBatisRepository
public interface ${table.className}Dao extends BaseMybatisDao<${table.className}>{

}
