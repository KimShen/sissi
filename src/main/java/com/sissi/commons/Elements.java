package com.sissi.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DBCursor;
import com.sissi.persistent.PersistentElement;
import com.sissi.protocol.Element;

/**
 * @author kim 2014年3月6日
 */
public class Elements extends ArrayList<Element> {

	private final static long serialVersionUID = 1L;

	public Elements(DBCursor cursor, List<PersistentElement> elements) {
		super();
		try {
			while (cursor.hasNext()) {
				Map<String, Object> each = Extracter.asMap(cursor.next());
				for (PersistentElement element : elements) {
					if (element.isSupport(each)) {
						this.add(element.read(each));
					}
				}
			}
		} finally {
			cursor.close();
		}
	}
}