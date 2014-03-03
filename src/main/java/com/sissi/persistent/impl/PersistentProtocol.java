package com.sissi.persistent.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElement;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
abstract class PersistentProtocol implements PersistentElement {
	
	protected final String tip;

	protected final JIDBuilder jidBuilder;

	private final Class<? extends Element> support;

	public PersistentProtocol(Class<? extends Element> support, JIDBuilder jidBuilder, String tip) {
		super();
		this.tip = tip;
		this.support = support;
		this.jidBuilder = jidBuilder;
	}

	protected Element read(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, PersistentElementBox.fieldId)).setFrom(this.toString(storage, PersistentElementBox.fieldFrom)).setTo(this.toString(storage, PersistentElementBox.fieldTo)).setType(this.toString(storage, PersistentElementBox.fieldType));
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(PersistentElementBox.fieldId, element.getId());
		entity.put(PersistentElementBox.fieldDelay, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		entity.put(PersistentElementBox.fieldFrom, this.jidBuilder.build(element.getFrom()).asStringWithBare());
		entity.put(PersistentElementBox.fieldTo, this.jidBuilder.build(element.getTo()).asStringWithBare());
		entity.put(PersistentElementBox.fieldClass, element.getClass().getSimpleName());
		entity.put(PersistentElementBox.fieldType, element.getType());
		entity.put(PersistentElementBox.fieldActivate, true);
		entity.put(PersistentElementBox.fieldRetry, 0);
		return entity;
	}

	@Override
	public boolean isSupport(Map<String, Object> storage) {
		return this.support.getSimpleName().equals(storage.get(PersistentElementBox.fieldClass));
	}

	public boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	private String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}