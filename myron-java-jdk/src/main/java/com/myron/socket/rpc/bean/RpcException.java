package com.myron.socket.rpc.bean;

import java.io.Serializable;

public class RpcException extends Exception implements Serializable{
	private static final long serialVersionUID = 1L;


	public RpcException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RpcException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RpcException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RpcException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
