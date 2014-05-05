package com.sissi.server.impl;

import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kim 2013年12月23日
 */
public class Main {

	private final static String prefix = "classpath:";

	private final static String dir = "configs";

	private final static String loading = "config-loading.xml";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(prefix + dir + File.separatorChar + loading).getBean(ChainedServer.class).start();
	}
}