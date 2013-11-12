package com.sissi.addressing.impl;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.JID;

/**
 * @author kim 2013-11-1
 */
public class InMemoryAddressing implements Addressing {

	private Log log = LogFactory.getLog(this.getClass());

	private LRUMap addressings;

	public InMemoryAddressing(Integer poolSize) {
		this.addressings = new LRUMap(poolSize);
	}

	public Context join(Context context) {
		this.addressings.put(context.jid().asStringWithNaked(), context);
		this.log.debug("JID " + context.jid().asStringWithNaked() + " joined the group");
		return context;
	}

	@Override
	public Context find(JID jid) {
		Context context = (Context) this.addressings.get(jid.asStringWithNaked());
		this.logIfNull(jid, context);
		return context;
	}

	private void logIfNull(JID jid, Context context) {
		if (context == null && this.log.isWarnEnabled()) {
			this.log.warn("JID " + jid.asStringWithNaked() + " could not match context");
		}
	}

	public Boolean online(JID jid) {
		return this.find(jid) != null;
	}
}
