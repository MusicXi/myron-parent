package com.myron.db.mybatis.bean;

import java.io.Serializable;
import java.util.List;

import com.myron.common.util.StringUtils;

/**
 * 分页参数类
 * <li>提交的参数:</li>
 * <li>private int start;//开始记录,初始位置:0</li>
 * <li>private int length;//每页多少记录数</li>
 * <li>private int currentPage;//当前页</li>
 * 
 * <li>查询后回填参数:</li>
 * <li>private int totalPage;//总页数</li>
 * <li>private int totalCount;//总记录数</li>
 * @param <T>
 * 
 */
public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 默认分页大小*/
	public static final int DEFAULT_LENGTH = 10;	
	
	//提交的分页查询参数
	protected int start;					//开始记录
	protected int length;					//每页多少记录数
	protected int currentPage;				//当前页
	
	//分页插件回填的数据
	protected int totalPage;				//总页数
	protected int totalCount;				//总记录数
	
	//排序参数
	protected String sortName;				//排序列名
	protected String sortOrder;     		//排序方式
	
	// 记录封装
	protected List<T> resultList;			//查询结果集

    public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
    	this.currentPage=currentPage;
    	//this.currentPage=currentPage==0?1: currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    
    public String getSortName() {
		return StringUtils.isBlank(sortName) ? null : sortName.trim();
	}

	public void setSortName(String sortName) {
		this.sortName = StringUtils.toUnderscoreName(sortName);;			
		
	}

	public String getSortOrder() {
		return StringUtils.isBlank(sortOrder) ? null : sortOrder.trim();
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 设置默认排序方式,当前端无指定排序方式时使用
	 * @param sortName
	 * @param sortOrder
	 */
	public void setDefaultSort(String sortName, String sortOrder){
		if(StringUtils.isBlank(this.sortName)&& StringUtils.isBlank(this.sortOrder)){
			this.setSortName(sortName);
			this.setSortOrder(sortOrder);		
		}
	}
	
	@Override
	public String toString() {
		return "Page [start(开始)=" + start + ", length(分页大小)=" + length + ", currentPage(当前页)="
				+ currentPage + ", totalPage(总页数)=" + totalPage + ", totalCount(总记录)="
				+ totalCount + "]";
	}
    
    

}

