package com.sissi.config.impl;

import java.util.Map;

import com.sissi.config.Config;

/**
 * @author kim 2013-11-15
 */
public class MongoConfig implements Config {

	public final static String D_NAME = "DB_NAME";

	public final static String C_NAME = "CL_NAME";

	private Map<String, String> configs;

	public MongoConfig(Map<String, String> configs) {
		super();
		this.configs = configs;
	}

	@Override
	public String get(String key) {
		return this.configs.get(key);
	}

}
