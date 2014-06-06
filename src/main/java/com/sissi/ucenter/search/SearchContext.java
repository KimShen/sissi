package com.sissi.ucenter.search;

import com.sissi.field.Fields;

/**
 * @author kim 2014年6月6日
 */
public interface SearchContext {

	public <T extends Fields> T search(Fields source, Fields target, Class<T> clazz);
}
