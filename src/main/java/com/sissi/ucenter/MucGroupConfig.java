package com.sissi.ucenter;

/**
 * @author kim 2014年2月20日
 */
public interface MucGroupConfig {

	public final static String HIDDEN = "hidden";
	
	public boolean allowed(String key, Object value);

	public String mapping(String affiliation);
}
