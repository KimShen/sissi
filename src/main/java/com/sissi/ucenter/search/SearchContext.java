package com.sissi.ucenter.search;

import com.sissi.field.Fields;

/**
 * 用户搜索
 * 
 * @author kim 2014年6月6日
 */
public interface SearchContext {

	/**
	 * @param source 搜索条件
	 * @param target 搜索结果
	 * @param clazz
	 * @return
	 */
	public <T extends Fields> T search(Fields source, Fields target, Class<T> clazz);
}
