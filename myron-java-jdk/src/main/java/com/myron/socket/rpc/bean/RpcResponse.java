package com.myron.socket.rpc.bean;

import java.io.Serializable;

/**
 * 封装 RPC 响应
 * @author lin.r.x
 *
 */
public class RpcResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String requestId;
	private Exception exception;
	private Object result;

	public boolean hasException() {
		return exception != null;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
