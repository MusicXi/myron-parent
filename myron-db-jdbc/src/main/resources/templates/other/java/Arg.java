package ${modelPath}.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class ${dsTable.methodTableName}Arg {
    private String pk_name = "${dsTable.idColumn.field}";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<${dsTable.methodTableName}Criteria> oredCriteria;

    public ${dsTable.methodTableName}Arg() {
        oredCriteria = new ArrayList<${dsTable.methodTableName}Criteria>();
    }

    public void setPk_name(String pk_name) {
        this.pk_name = StringEscapeUtils.escapeSql(pk_name);
    }

    public String getPk_name() {
        return pk_name;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = StringEscapeUtils.escapeSql(orderByClause);
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = StringEscapeUtils.escapeSql(groupByClause);
    }

    public String geGroupByClause() {
        return groupByClause;
    }

    public void setColumns(String columns) {
        this.columns = StringEscapeUtils.escapeSql(columns);
    }

    public String geColumns() {
        return columns;
    }

    public void setCountsql1(String countsql1) {
        this.countsql1 = StringEscapeUtils.escapeSql(countsql1);
    }

    public String geCountsql1() {
        return countsql1;
    }

    public void setCountsql2(String countsql2) {
        this.countsql2 = StringEscapeUtils.escapeSql(countsql2);
    }

    public String geCountsql2() {
        return countsql2;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public void setRowEnd(int rowEnd) {
        this.rowEnd = rowEnd;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public List<${dsTable.methodTableName}Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(${dsTable.methodTableName}Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public ${dsTable.methodTableName}Criteria or() {
    	${dsTable.methodTableName}Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public ${dsTable.methodTableName}Criteria createCriteria() {
    	${dsTable.methodTableName}Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected ${dsTable.methodTableName}Criteria createCriteriaInternal() {
    	${dsTable.methodTableName}Criteria criteria = new ${dsTable.methodTableName}Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        groupByClause = null;
        columns = null;
        countsql1 = null;
        countsql2 = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<${dsTable.methodTableName}Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<${dsTable.methodTableName}Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<${dsTable.methodTableName}Criterion> getAllCriteria() {
            return criteria;
        }

        public List<${dsTable.methodTableName}Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new ${dsTable.methodTableName}Criterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new ${dsTable.methodTableName}Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new ${dsTable.methodTableName}Criterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new ${dsTable.methodTableName}Criterion(condition, value1, value2));
        }

        public ${dsTable.methodTableName}Criteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (${dsTable.methodTableName}Criteria) this;
        }
        <#list dsTable.dsColumnList as column>
        public ${dsTable.methodTableName}Criteria and${column.methodProperty}IsNull() {
            addCriterion("${column.field} is null");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}IsNotNull() {
            addCriterion("${column.field} is not null");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}EqualTo(${column.javaType} value) {
            addCriterion("${column.field} =", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotEqualTo(${column.javaType} value) {
            addCriterion("${column.field} <>", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}GreaterThan(${column.javaType} value) {
            addCriterion("${column.field} >", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}GreaterThanOrEqualTo(${column.javaType} value) {
            addCriterion("${column.field} >=", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}LessThan(${column.javaType} value) {
            addCriterion("${column.field} <", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}LessThanOrEqualTo(${column.javaType} value) {
            addCriterion("${column.field} <=", value, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}Like(${column.javaType} value) {
            addCriterion("${column.field} like ", value, "${column.field}", 1);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotLike(${column.javaType} value) {
            addCriterion("${column.field}  not like ", value, "${column.field}", 1);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}LeftLike(${column.javaType} value) {
            addCriterion("${column.field} like ", value, "${column.field}", 0);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotLeftLike(${column.javaType} value) {
            addCriterion("${column.field}  not like ", value, "${column.field}", 0);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}RightLike(${column.javaType} value) {
            addCriterion("${column.field} like ", value, "${column.field}", 2);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotRightLike(${column.javaType} value) {
            addCriterion("${column.field}  not like ", value, "${column.field}", 2);
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}In(List<${column.javaType}> values) {
            addCriterion("${column.field}  in ", values, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotIn(List<${column.javaType}> values) {
            addCriterion("${column.field} not in ", values, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}Between(${column.javaType} value1, ${column.javaType} value2) {
            addCriterion("${column.field} between ", value1, value2, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }

        public ${dsTable.methodTableName}Criteria and${column.methodProperty}NotBetween(${column.javaType} value1, ${column.javaType} value2) {
            addCriterion("${column.field} not between ", value1, value2, "${column.field}");
            return (${dsTable.methodTableName}Criteria) this;
        }
        </#list>

    }

    public static class ${dsTable.methodTableName}Criteria extends GeneratedCriteria {

        protected ${dsTable.methodTableName}Criteria() {
            super();
        }
    }

    public static class ${dsTable.methodTableName}Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected ${dsTable.methodTableName}Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected ${dsTable.methodTableName}Criterion(String condition, Object value, int likeType) {
            this.condition = condition;
            if (likeType == 0) {
                this.value = "%" + value;
            }
            else if (likeType == 1) {
                this.value = "%" + value + "%";
            }
            else {
                this.value = value + "%";
            }
            this.typeHandler = null;
            this.singleValue = true;

        }

        protected ${dsTable.methodTableName}Criterion(String condition, Object value,
                String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            }
            else {
                this.singleValue = true;
            }
        }

        protected ${dsTable.methodTableName}Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected ${dsTable.methodTableName}Criterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected ${dsTable.methodTableName}Criterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}