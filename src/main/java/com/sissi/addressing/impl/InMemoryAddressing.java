package com.sissi.addressing.impl;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2013-11-1
 */
public class InMemoryAddressing implements Addressing {

	private Log log = LogFactory.getLog(this.getClass());

	private LRUMap addressings;

	public InMemoryAddressing(Integer poolSize) {
		this.addressings = new LRUMap(poolSize);
	}

	public JIDContext join(JIDContext context) {
		this.addressings.put(context.jid().asStringWithBare(), context);
		this.log.debug("JID " + context.jid().asStringWithBare() + " joined the group");
		return context;
	}

	@Override
	public JIDContext find(JID jid) {
		JIDContext context = (JIDContext) this.addressings.get(jid.asStringWithBare());
		this.logIfNull(jid, context);
		return context;
	}

	public Boolean isOnline(JID jid) {
		return this.find(jid) != null;
	}
	
	private void logIfNull(JID jid, JIDContext context) {
		if (context == null && this.log.isWarnEnabled()) {
			this.log.warn("JID " + jid.asStringWithBare() + " could not match context");
		}
	}
}
