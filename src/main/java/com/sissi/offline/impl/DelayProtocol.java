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

	protected final static String FIELD_CLASS = "class";

	protected final static String FIELD_DELAY = "delay";

	protected final static String FIELD_FROM = "from";

	protected final static String FIELD_TYPE = "type";

	protected final static String FIELD_TO = "to";

	protected final static String FIELD_ID = "id";

	private final Class<? extends Element> support;

	private String offline;

	private JIDBuilder jidBuilder;

	public DelayProtocol(Class<? extends Element> support) {
		super();
		this.support = support;
	}

	protected String getOffline() {
		return this.offline;
	}

	public void setOffline(String offline) {
		this.offline = offline;
	}

	protected JIDBuilder getJidBuilder() {
		return jidBuilder;
	}

	public void setJidBuilder(JIDBuilder jidBuilder) {
		this.jidBuilder = jidBuilder;
	}

	protected Element based(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, FIELD_ID)).setFrom(this.toString(storage, FIELD_FROM)).setTo(this.toString(storage, FIELD_TO)).setType(this.toString(storage, FIELD_TYPE));
	}

	protected Map<String, Object> based(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(FIELD_ID, element.getId());
		entity.put(FIELD_FROM, this.jidBuilder.build(element.getFrom()).asStringWithBare());
		entity.put(FIELD_TO, this.jidBuilder.build(element.getTo()).asStringWithBare());
		entity.put(FIELD_TYPE, element.getType());
		entity.put(FIELD_DELAY, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put(FIELD_CLASS, element.getClass().getSimpleName());
		return entity;
	}

	@Override
	public Boolean isSupport(Map<String, Object> storage) {
		return this.support.getSimpleName().equals(storage.get(FIELD_CLASS));
	}

	public Boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}