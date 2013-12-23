package com.sissi.offline.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.context.JIDBuilder;
import com.sissi.offline.DelayElement;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public abstract class DelayProtocol implements DelayElement {

	protected final String hit;

	protected final JIDBuilder jidBuilder;

	public DelayProtocol(String hit, JIDBuilder jidBuilder) {
		super();
		this.hit = hit;
		this.jidBuilder = jidBuilder;
	}

	protected Element based(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, "id")).setFrom(this.toString(storage, "from")).setTo(this.toString(storage, "to")).setType(this.toString(storage, "type"));
	}

	protected Map<String, Object> based(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("id", element.getId());
		entity.put("from", element.getFrom());
		entity.put("to", element.getTo());
		entity.put("type", element.getType());
		entity.put("delay", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put("class", element.getClass().getSimpleName());
		return entity;
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}