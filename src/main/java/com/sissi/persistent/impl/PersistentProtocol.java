package com.sissi.persistent.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElement;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
abstract class PersistentProtocol implements PersistentElement {

	private final boolean full;

	protected final String tip;

	protected final JIDBuilder jidBuilder;

	private final Class<? extends Element> support;

	public PersistentProtocol(Class<? extends Element> support, JIDBuilder jidBuilder, String tip, boolean full) {
		super();
		this.tip = tip;
		this.full = full;
		this.support = support;
		this.jidBuilder = jidBuilder;
	}

	protected Element read(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, PersistentElementBox.fieldId)).setFrom(this.toString(storage, MongoConfig.FIELD_FROM)).setTo(this.toString(storage, MongoConfig.FIELD_TO)).setType(this.toString(storage, MongoConfig.FIELD_TYPE));
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(MongoConfig.FIELD_ACTIVATE, true);
		entity.put(MongoConfig.FIELD_TYPE, element.getType());
		entity.put(MongoConfig.FIELD_TIMESTAMP, System.currentTimeMillis());
		entity.put(MongoConfig.FIELD_CLASS, element.getClass().getSimpleName());
		entity.put(PersistentElementBox.fieldResend, 0);
		entity.put(PersistentElementBox.fieldId, element.getId());
		entity.put(PersistentElementBox.fieldDelay, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		JID to = this.jidBuilder.build(element.getTo());
		JID from = this.jidBuilder.build(element.getFrom());
		entity.put(MongoConfig.FIELD_TO, this.full ? to.asString() : to.asStringWithBare());
		entity.put(MongoConfig.FIELD_FROM, this.full ? from.asString() : from.asStringWithBare());
		return entity;
	}

	@Override
	public boolean isSupport(Map<String, Object> storage) {
		return this.support.getSimpleName().equals(storage.get(MongoConfig.FIELD_CLASS));
	}

	public boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}