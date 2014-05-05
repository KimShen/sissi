package com.sissi.context.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.mongodb.DBCursor;
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDs;

/**
 * 线程安全的JIDs实现
 * 
 * @author kim 2014年3月6日
 */
public class ShareJIDs implements JIDs {

	private final AtomicInteger counter = new AtomicInteger();

	private final List<String> resources = new ArrayList<String>();

	private final JID jid;

	public ShareJIDs(JID source, DBCursor cursor) {
		this.jid = source.bare();
		try {
			while (cursor.hasNext()) {
				this.resources.add(MongoUtils.asString(cursor.next(), Dictionary.FIELD_RESOURCE));
			}
		} finally {
			cursor.close();
		}
	}

	public ShareJIDs(JID source, String[] resources) {
		this.jid = source.bare();
		for (String resource : resources) {
			this.resources.add(resource);
		}
	}

	public JID jid() {
		return this.jid;
	}

	public Iterator<JID> iterator() {
		return new JIDIterator();
	}

	public boolean isEmpty() {
		return this.resources.isEmpty();
	}

	public boolean lessThan(Integer counter) {
		return this.resources.size() < counter;
	}

	@Override
	public boolean same(JID jid) {
		return this.like(jid) && this.resources.contains(jid.resource());
	}

	public boolean like(JID jid) {
		return this.jid.like(jid);
	}

	private boolean hasNext() {
		return this.counter.get() < this.resources.size();
	}

	private JID next() {
		return this.jid.resource(this.resources.get(this.counter.incrementAndGet() - 1));
	}

	private class JIDIterator implements Iterator<JID> {

		@Override
		public boolean hasNext() {
			return ShareJIDs.this.hasNext();
		}

		@Override
		public JID next() {
			return ShareJIDs.this.next();
		}

		@Override
		public void remove() {
		}
	}
}