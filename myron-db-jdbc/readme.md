代码生成工具的设计思路及原则
1.生成代码顺序,采用人为编写代码的顺序,即依次从持久层到到控制层及显示层。 测试顺序也是采用这个方案
	数据库table->bean->dao->service->controller->前端代码
2.保证生成代码最大可能的可用性,能实现功能情况下，尽量少使用无关的jar包，必须要用时,尽量避免引入maven公共仓库无法引用的私人jar包。
3.要有保证当前代码生成器可用的maven 依赖的清单列表
4.封装描述生成类和类属性信息的Table和Column对象的 包含的属性应该明确而又意义,有利于他人根据类的"元数据"二次开发自定义模板

2016-9-18 
代码生成器完成功能：
	1.定义用于描述Java对象的属性信息和类信息的的Column和Table的对象
	2.封装的jdbc模板类可实现对任意表的类属性信息进行查询,并封装结果集
		List<Column> columnList=jdbcTemplate.queryColumn(tableName);
	
	3.实现根据模板自动生成源代码的模板:
		JavaBean.java
		JavaDao.java
		JavaMapper.xml
		JavaService.java
		JavaServiceImpl.java
新增需求:生成对应Junit spring 单元测试类
新增技术研究：junit的springmvc单元测试如何进行

2016-10-1
优化:
普通javabean去除basebean的继承关系,恢复独立性。

新需求:
1.根据数据库字段长度校验前端输入项
2.表单编辑时主键不可编辑,关闭后重置状态
3.Excel导入功能测试
4.用户管理新增委派角色功能

2017-03-27
1.去除JavaDao继承BaseMybatisDao,添加BaseMybatis中的基础方法,增加生成的继承操作可修改的灵活性。
2.去除所有接口方法定义的public描述符
3.显示声明实体类的无参构造方法

2017-04-06
1.编码规范性调整 参考《阿里巴巴Java开发手册》(正式版)
2.XxxServiceImpl 添加、删除方法 添加获取主键
3.增加批添加操作