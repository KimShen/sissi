package com.sissi.netty.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sissi.netty.ServerStart;

/**
 * @author kim 2013-11-19
 */
public class Server {

	private static List<String> reading() {
		List<String> files = new ArrayList<String>();
		for (String file : new File(Thread.currentThread().getContextClassLoader().getResource("config").getFile()).list()) {
			files.add("classpath:config/" + file);
		}
		return files;
	}

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(reading().toArray(new String[] {})).getBean(ServerStart.class).start();
	}
}