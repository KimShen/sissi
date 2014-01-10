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

	final static String CLASS = "class";

	final static String DELAY = "delay";

	final static String BASE_ID = "id";

	final static String BASE_FROM = "from";

	final static String BASE_TO = "to";

	final static String BASE_TYPE = "type";

	private String offline;

	private JIDBuilder jidBuilder;

	protected String getOffline() {
		return offline;
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
		return element.setId(this.toString(storage, BASE_ID)).setFrom(this.toString(storage, BASE_FROM)).setTo(this.toString(storage, BASE_TO)).setType(this.toString(storage, BASE_TYPE));
	}

	protected Map<String, Object> based(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(BASE_ID, element.getId());
		entity.put(BASE_FROM, this.jidBuilder.build(element.getFrom()).asStringWithBare());
		entity.put(BASE_TO, this.jidBuilder.build(element.getTo()).asStringWithBare());
		entity.put(BASE_TYPE, element.getType());
		entity.put(DELAY, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put(CLASS, element.getClass().getSimpleName());
		return entity;
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}