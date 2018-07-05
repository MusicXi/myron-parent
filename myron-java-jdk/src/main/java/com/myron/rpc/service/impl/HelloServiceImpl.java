package com.myron.rpc.service.impl;

import com.myron.rpc.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHi(String name) {
		return "Hi, " + name;
	}

	@Override
	public int plus(int x, int y) {
		return x + y;
	}

}
