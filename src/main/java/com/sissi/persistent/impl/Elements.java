package com.sissi.persistent.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DBCursor;
import com.sissi.config.impl.MongoUtils;
import com.sissi.persistent.PersistentElement;
import com.sissi.protocol.Element;

/**
 * @author kim 2014年3月6日
 */
public class Elements extends ArrayList<Element> {

	private final static long serialVersionUID = 1L;

	public Elements(DBCursor cursor, List<PersistentElement> elements) {
		super();
		try (DBCursor iterator = cursor) {
			while (iterator.hasNext()) {
				Map<String, Object> each = MongoUtils.asMap(iterator.next());
				for (PersistentElement element : elements) {
					if (element.isSupport(each)) {
						this.add(element.read(each));
					}
				}
			}
		}
	}
}