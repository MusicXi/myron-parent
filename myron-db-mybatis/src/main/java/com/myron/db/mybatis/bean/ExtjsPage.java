package com.myron.db.mybatis.bean;

import com.myron.common.util.JsonUtil;


/**
 * extjs的分页参数传递给mybatis的分页
 * @author Administrator
 *
 */
public class ExtjsPage<T> extends Page<T>{
	private static final long serialVersionUID = 1L;
	
//	private int start;		//与mybatis属性一致不用添加
	private int page;
	private int limit;
	private String sort;	//extjs 提交的分页参数

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.currentPage=page;
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.length=limit;
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		//[{"property":"username","direction":"ASC"}]
		if(sort.length()==0){
			return;
		}
		String str=sort.substring(1, sort.length()-1);
		this.sort = sort;
		this.setSortName((String) JsonUtil.toMap(str).get("property"));
		this.setSortOrder((String) JsonUtil.toMap(str).get("direction"));
	}
	
	public static void main(String[] args){
		String sort="[{\"property\":\"username\",\"direction\":\"ASC\"}]";
		String str=sort.substring(1, sort.length()-1);
		System.out.println(str);
		System.out.println("ExtjsPage.main():"+JsonUtil.toMap(str).get("property"));
		
	}
	
	
	
}
