package com.sissi.netty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kim 2013-11-19
 */
public class Server {

	public void run() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(this.reading().toArray(new String[] {}));
		context.getBean(ServerStart.class).start();
	}

	private List<String> reading() {
		List<String> files = new ArrayList<String>();
		for (String file : new File(Thread.currentThread().getContextClassLoader().getResource("config").getFile()).list()) {
			files.add("classpath:config/" + file);
		}
		return files;
	}

	public static void main(String[] args) throws Exception {
		new Server().run();
	}
}