package com.myron.socket.rpc.bean;

import java.io.Serializable;

/**
 * 远程调用协议    
 * @Author:       lin.r.x   
 * @CreateDate:   2017年6月20日 下午4:55:31   
 * @UpdateUser:   Administrator  
 * @UpdateDate:   2017年6月20日 下午4:55:31   
 * @UpdateRemark: 说明本次修改内容  
 * @Version:      v1.0
 */
public class RpcProtocol implements Serializable {
	private static final long serialVersionUID = 1L;
	//消息头 --元信息
	
	//消息体 --调用信息
	private String serviceInterface;
	private String methodName;
	private Class<?>[] ParameterTypes;
	private Object[] args;
	
//	private Integer timeout;
	
	
	private RpcProtocol(String serviceInterface, String methodName,
			Class<?>[] parameterTypes, Object[] args) {
		super();
		this.serviceInterface = serviceInterface;
		this.methodName = methodName;
		ParameterTypes = parameterTypes;
		this.args = args;
	}

	public static RpcProtocol decode(String serviceInterface, String methodName,
			Class<?>[] parameterTypes, Object[] args) {
		return new RpcProtocol(serviceInterface, methodName, parameterTypes, args);
	}
	
	public String getServiceInterface() {
		return serviceInterface;
	}
	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return ParameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		ParameterTypes = parameterTypes;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	
}
