package com.sissi.netty.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;

import com.sissi.netty.ServerStart;

/**
 * @author kim 2013-11-19
 */
public class Server {

	private final static String PREFIX = "classpath:";

	private static String[] reading() throws IOException {
		List<String> configs = new ArrayList<String>();
		for (String each : IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(System.getProperty("loading", "loading.properties")), Charset.forName("UTF-8"))) {
			configs.add(ResourceUtils.getURL(PREFIX + each).toString());
		}
		return configs.toArray(new String[] {});
	}

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(reading()).getBean(ServerStart.class).start();
	}
}