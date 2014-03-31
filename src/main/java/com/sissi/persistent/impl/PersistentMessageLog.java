package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;

/**
 * @author kim 2014年3月31日
 */
public class PersistentMessageLog extends PersistentMessage {

	public PersistentMessageLog(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip);
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		entity.put(PersistentElementBox.fieldAck, false);
		return entity;
	}
}
