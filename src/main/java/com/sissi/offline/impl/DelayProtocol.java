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
abstract class DelayProtocol implements DelayElement {

	protected final static String fieldClass = "class";

	protected final static String fieldDelay = "delay";

	protected final static String fieldFrom = "from";

	protected final static String fieldType = "type";

	protected final static String fieldTo = "to";

	protected final static String fieldId = "id";

	protected final String offline;

	private final Class<? extends Element> support;

	private final JIDBuilder jidBuilder;

	public DelayProtocol(Class<? extends Element> support, JIDBuilder jidBuilder, String offline) {
		super();
		this.support = support;
		this.offline = offline;
		this.jidBuilder = jidBuilder;
	}

	protected Element read(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, fieldId)).setFrom(this.toString(storage, fieldFrom)).setTo(this.toString(storage, fieldTo)).setType(this.toString(storage, fieldType));
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(fieldId, element.getId());
		entity.put(fieldFrom, this.jidBuilder.build(element.getFrom()).asStringWithBare());
		entity.put(fieldTo, this.jidBuilder.build(element.getTo()).asStringWithBare());
		entity.put(fieldType, element.getType());
		entity.put(fieldDelay, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put(fieldClass, element.getClass().getSimpleName());
		return entity;
	}

	@Override
	public boolean isSupport(Map<String, Object> storage) {
		return this.support.getSimpleName().equals(storage.get(fieldClass));
	}

	public boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}