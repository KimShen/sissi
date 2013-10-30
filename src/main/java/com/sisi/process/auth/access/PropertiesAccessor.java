package com.sisi.process.auth.access;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.process.auth.Accessor;
import com.sisi.process.auth.User;

/**
 * @author kim 2013-10-24
 */
public class PropertiesAccessor implements Accessor {

	private final Properties properties = new Properties();

	private Log log = LogFactory.getLog(this.getClass());

	public PropertiesAccessor() {
		super();
		InputStream users = null;
		try {
			users = Thread.currentThread().getContextClassLoader().getResourceAsStream("auth.properties");
			this.properties.load(users);
		} catch (IOException e) {
			log.error(e);
		} finally {
			IOUtils.closeQuietly(users);
		}
	}

	@Override
	public boolean access(User user) {
		this.log.debug("Loading all properties: " + this.properties);
		String username = user.getUser().intern();
		String password = this.properties.getProperty(username);
		this.log.info("Username: " + username + " And Password: " + user.getPass() + " / " + password);
		return user.getPass().equals(password);
	}
}
