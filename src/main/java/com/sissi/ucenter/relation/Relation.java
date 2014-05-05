package com.sissi.ucenter.relation;

import java.util.Map;

/**
 * @author kim 2013年12月30日
 */
public interface Relation {

	public String jid();

	/**
	 * 关系名称
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 是否已激活
	 * 
	 * @return
	 */
	public boolean activate();

	public Map<String, Object> plus();

	public <T extends Relation> T cast(Class<T> clazz);
}