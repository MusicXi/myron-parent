package com.myron.db.redis.mq.bean;

import java.io.Serializable;
import java.util.Date;

public class SmsMessageVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
    private Integer smsId;

    //手机号
    private String mobile;

    //类型，1：验证码 2：订单通知
    private Byte type;

    //短信创建时间
    private Date createDate;

    //短信消息处理时间
    private Date processTime;

    //短信状态，1：未发送 2：发送成功 3：发送失败
    private String status;

    //短信内容
    private String content;

	public Integer getSmsId() {
		return smsId;
	}

	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SmsMessageVo [smsId=" + smsId + ", mobile=" + mobile
				+ ", type=" + type + ", createDate=" + createDate
				+ ", processTime=" + processTime + ", status=" + status
				+ ", content=" + content + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    


}
