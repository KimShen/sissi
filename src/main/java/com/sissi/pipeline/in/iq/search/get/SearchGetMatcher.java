package com.sissi.pipeline.in.iq.search.get;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.search.Search;

/**
 * 匹配表单类型(复杂表单/简易表单)
 * 
 * @author kim 2014年6月7日
 */
public class SearchGetMatcher extends ClassMatcher {

	private final boolean multi;

	/**
	 * @param multi 是否为复杂表单
	 */
	public SearchGetMatcher(boolean multi) {
		super(Search.class);
		this.multi = multi;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Search.class).form(this.multi);
	}
}
