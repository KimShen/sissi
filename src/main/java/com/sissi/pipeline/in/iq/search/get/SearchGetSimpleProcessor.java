package com.sissi.pipeline.in.iq.search.get;

import com.sissi.context.JIDContext;
import com.sissi.field.impl.BeanFields;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.search.Search;
import com.sissi.ucenter.search.SearchContext;

/**
 * Simple用户搜索
 * 
 * @author kim 2014年6月6日
 */
public class SearchGetSimpleProcessor implements Input {

	private final SearchContext searchContext;

	public SearchGetSimpleProcessor(SearchContext searchContext) {
		super();
		this.searchContext = searchContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Search search = protocol.cast(Search.class);
		BeanFields fields = this.searchContext.search(search, new BeanFields(false), BeanFields.class);
		context.write(search.clear().add(fields).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
