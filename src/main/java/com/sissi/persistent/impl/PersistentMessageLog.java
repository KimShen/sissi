package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;

/**
 * @author kim 2014年4月9日
 */
public class PersistentMessageLog extends PersistentMessage {

	public PersistentMessageLog(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip);
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		entity.put(MongoConfig.FIELD_ACTIVATE, false);
		return entity;
	}
}
