package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;

/**
 * Message 日志, {"activate":false}
 * 
 * @author kim 2014年4月9日
 */
public class PersistentMessageLog extends PersistentMessage {

	public PersistentMessageLog(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip);
	}

	/*
	 * Activate = false
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#write(com.sissi.protocol.Element)
	 */
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		entity.put(Dictionary.FIELD_ACTIVATE, false);
		return entity;
	}
}
