define({
  <#list dsTable.dsColumnList as column>
	${column.column}:"${column.comment}",
  </#list>
	${dsTable.tableName}:"${dsTable.methodTableName}"
});