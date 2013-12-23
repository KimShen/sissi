package com.sissi.server.impl;

import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kim 2013年12月23日
 */
public class MainServer {

	private final static String PREFIX = "classpath:";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(PREFIX + "configs" + File.separatorChar + "config-loading.xml");
		context.getBean(ChainedServerStarter.class).start();
	}
}