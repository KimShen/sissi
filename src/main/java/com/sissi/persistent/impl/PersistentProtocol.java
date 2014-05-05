package com.sissi.persistent.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElement;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
abstract class PersistentProtocol implements PersistentElement {

	private final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");

	protected final String tip;

	protected final JIDBuilder jidBuilder;

	private final boolean bare;

	private final Class<? extends Element> support;

	public PersistentProtocol(Class<? extends Element> support, JIDBuilder jidBuilder, String tip, boolean bare) {
		super();
		this.tip = tip;
		this.bare = bare;
		this.support = support;
		this.jidBuilder = jidBuilder;
	}

	/*
	 * {"id":Xxx}
	 * 
	 * @see com.sissi.persistent.PersistentElement#query(com.sissi.protocol.Element)
	 */
	public Map<String, Object> query(Element element) {
		return MongoUtils.asMap(BasicDBObjectBuilder.start(Dictionary.FIELD_PID, element.getId()).get());
	}

	/**
	 * ID,FROM,TO,TYPE
	 * 
	 * @param storage
	 * @param element
	 * @return
	 */
	protected Element read(Map<String, Object> storage, Element element) {
		return element.setId(this.toString(storage, Dictionary.FIELD_PID)).setFrom(this.toString(storage, Dictionary.FIELD_FROM)).setTo(this.toString(storage, Dictionary.FIELD_TO)).setType(this.toString(storage, Dictionary.FIELD_TYPE));
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put(Dictionary.FIELD_RESEND, 0);
		entity.put(Dictionary.FIELD_ACTIVATE, true);
		entity.put(Dictionary.FIELD_PID, element.getId());
		entity.put(Dictionary.FIELD_TYPE, element.getType());
		entity.put(Dictionary.FIELD_TIMESTAMP, System.currentTimeMillis());
		entity.put(Dictionary.FIELD_DELAY, this.format.format(new Date()));
		entity.put(Dictionary.FIELD_CLASS, element.getClass().getSimpleName());
		JID to = this.jidBuilder.build(element.getTo());
		JID from = this.jidBuilder.build(element.getFrom());
		entity.put(Dictionary.FIELD_TO, this.bare ? to.asStringWithBare() : to.asString());
		entity.put(Dictionary.FIELD_FROM, this.bare ? from.asStringWithBare() : from.asString());
		return entity;
	}

	/*
	 * Class比较
	 * 
	 * @see com.sissi.persistent.PersistentElement#isSupport(java.util.Map)
	 */
	@Override
	public boolean isSupport(Map<String, Object> element) {
		return this.support.getSimpleName().equals(element.get(Dictionary.FIELD_CLASS));
	}

	/*
	 * Class比较
	 * 
	 * @see com.sissi.persistent.PersistentElement#isSupport(com.sissi.protocol.Element)
	 */
	public boolean isSupport(Element element) {
		return this.support == element.getClass();
	}

	protected String toString(Map<String, Object> element, String key) {
		Object value = element.get(key);
		return value != null ? value.toString() : null;
	}
}