package com.sissi.ucenter;

import java.util.Map;

/**
 * @author kim 2013年12月30日
 */
public interface Relation {

	public String jid();

	public String name();

	public boolean activate();

	public Map<String, Object> plus();

	public <T extends Relation> T cast(Class<T> clazz);
}