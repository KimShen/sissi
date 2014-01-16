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

	protected final String clazz = "class";

	protected final String delay = "delay";

	protected final String baseId = "id";

	protected final String baseFrom = "from";

	protected final String baseTo = "to";

	protected final String baseType = "type";

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
		return element.setId(this.toString(storage, this.baseId)).setFrom(this.toString(storage, this.baseFrom)).setTo(this.toString(storage, this.baseTo)).setType(this.toString(storage, this.baseType));
	}

	protected Map<String, Object> based(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(this.baseId, element.getId());
		entity.put(this.baseFrom, this.jidBuilder.build(element.getFrom()).asStringWithBare());
		entity.put(this.baseTo, this.jidBuilder.build(element.getTo()).asStringWithBare());
		entity.put(this.baseType, element.getType());
		entity.put(this.delay, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put(this.clazz, element.getClass().getSimpleName());
		return entity;
	}

	@Override
	public Boolean isSupport(Map<String, Object> storage) {
		return this.support.getSimpleName().equals(storage.get(clazz));
	}

	public Boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}