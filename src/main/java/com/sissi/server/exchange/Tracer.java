package com.sissi.server.exchange;

import java.util.Map;

/**
 * @author kim 2014年5月8日
 */
public interface Tracer {

	public String id();

	public String target();

	public String initiator();

	public Map<String, Object> plus();
}
