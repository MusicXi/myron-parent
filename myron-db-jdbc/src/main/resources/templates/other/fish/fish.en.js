define({
  <#list dsTable.dsColumnList as column>
	${column.column}:"${column.property}",
  </#list>
	${dsTable.tableName}:"${dsTable.methodTableName}"
});