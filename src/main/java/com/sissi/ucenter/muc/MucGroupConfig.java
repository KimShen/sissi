package com.sissi.ucenter.muc;

/**
 * @author kim 2014年2月20日
 */
public interface MucGroupConfig {

	public final static String HIDDEN_ROLE = "hidden_role";

	public final static String HIDDEN_PURE = "hidden_pure";

	public final static String PASSWORD = "password";

	public final static String AFFILIATIONS = "affiliations";

	public boolean allowed(String key, Object value);

	public String mapping(String affiliation);
}
