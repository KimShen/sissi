package com.sissi.config;

/**
 * 配置取值器
 * 
 * @author kim 2013-11-15
 */
public interface Dictionary {

	/**
	 * 数据库名称
	 */
	public final static String D_NAME = "D_NAME";

	/**
	 * 数据集合名称
	 */
	public final static String C_NAME = "C_NAME";

	public final static String FIELD_ID = "_id";

	public final static String FIELD_TO = "to";

	public final static String FIELD_JID = "jid";

	/**
	 * Protocol id
	 */
	public final static String FIELD_PID = "id";

	/**
	 * 回执
	 */
	public final static String FIELD_ACK = "ack";

	/**
	 * Si.sid
	 */
	public final static String FIELD_SID = "sid";

	public final static String FIELD_FROM = "from";

	public final static String FIELD_PATH = "path";

	/**
	 * Si.name
	 */
	public final static String FIELD_NAME = "name";

	/**
	 * Si.host
	 */
	public final static String FIELD_HOST = "host";

	/**
	 * Si.size
	 */
	public final static String FIELD_SIZE = "size";

	public final static String FIELD_NICK = "nick";

	public final static String FIELD_NICKS = "nicks";

	public final static String FIELD_TYPE = "type";

	/**
	 * Message.body
	 */
	public final static String FIELD_BODY = "body";

	public final static String FIELD_WHOIS = "whois";

	public final static String FIELD_ROLE = "role";

	public final static String FIELD_ROLES = "roles";

	public final static String FIELD_GROUP = "group";

	public final static String FIELD_GROUPS = "groups";

	public final static String FIELD_SLAVE = "slave";

	public final static String FIELD_MASTER = "master";

	/**
	 * 延迟
	 */
	public final static String FIELD_DELAY = "delay";

	public final static String FIELD_CLASS = "class";

	public final static String FIELD_INDEX = "index";

	public final static String FIELD_RESULT = "result";

	public final static String FIELD_INVITE = "invite";

	public final static String FIELD_STATUS = "status";

	public final static String FIELD_THREAD = "thread";

	public final static String FIELD_PUBLIC = "public";

	public final static String FIELD_SOURCE = "source";

	/**
	 * MUC.reason
	 */
	public final static String FIELD_REASON = "reason";

	/**
	 * 重发次数
	 */
	public final static String FIELD_RESEND = "resend";

	public final static String FIELD_CONFIGS = "configs";

	public final static String FIELD_MAPPING = "mapping";

	public final static String FIELD_SUBJECT = "subject";

	public final static String FIELD_ACCOUNR = "account";

	public final static String FIELD_CREATOR = "creator";

	public final static String FIELD_MAXUSERS = "maxusers";

	public final static String FIELD_PRIORITY = "priority";

	public final static String FIELD_ACTIVATE = "activate";

	public final static String FIELD_RESOURCE = "resource";

	public final static String FIELD_USERNAME = "username";

	public final static String FIELD_PASSWORD = "password";

	public final static String FIELD_CONTINUE = "continue";

	public final static String FIELD_REGISTER = "register";

	public final static String FIELD_TIMESTAMP = "timestamp";

	public final static String FIELD_PERSISTENT = "persistent";

	public final static String FIELD_INFORMATION = "information";

	public final static String FIELD_INFORMATIONS = "informations";

	public final static String FIELD_AFFILIATION = "affiliation";

	public final static String FIELD_AFFILIATIONS = "affiliations";

	public Object get(String key);
}
