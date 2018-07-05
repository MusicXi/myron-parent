package ${modelPath}.db.dao.mapper;

import ${modelPath}.db.arg.${dsTable.methodTableName}Arg;
import ${modelPath}.db.po.${dsTable.methodTableName}PO;

import java.math.*;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface I${dsTable.methodTableName}Mapper {
    
    List<Integer> countByArg(${dsTable.methodTableName}Arg arg);

    List<${dsTable.methodTableName}PO> selectByArg(${dsTable.methodTableName}Arg arg);

    ${dsTable.methodTableName}PO selectByPrimaryKey(${dsTable.idColumn.javaType} id);

    List<${dsTable.methodTableName}PO> selectByArgAndPage(${dsTable.methodTableName}Arg arg, RowBounds rowBound);

    int insert(${dsTable.methodTableName}PO record);

    int insertSelective(${dsTable.methodTableName}PO record);

    int insertBatch(@Param("list") List<${dsTable.methodTableName}PO> records);

    int updateByArgSelective(@Param("record") ${dsTable.methodTableName}PO record,
            @Param("arg") ${dsTable.methodTableName}Arg arg);

    int updateByArg(@Param("record") ${dsTable.methodTableName}PO record,
            @Param("arg") ${dsTable.methodTableName}Arg arg);

    int updateByPrimaryKeySelective(${dsTable.methodTableName}PO record);

    int updateByPrimaryKey(${dsTable.methodTableName}PO record);
    
    int deleteByArg(${dsTable.methodTableName}Arg arg);
    
    int deleteByPrimaryKey(${dsTable.idColumn.javaType} id);
    
}