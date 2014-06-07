package com.sissi.pipeline.in.iq.search.get;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.field.Field;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XReported;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.protocol.iq.search.Search;
import com.sissi.ucenter.search.SearchContext;

/**
 * 表单用户查询
 * 
 * @author kim 2014年6月6日
 */
public class SearchGetMultiProcessor implements Input {

	private final XData data;

	private final SearchContext searchContext;

	/**
	 * @param reports 报表
	 * @param searchContext
	 */
	public SearchGetMultiProcessor(List<Field<?>> reports, SearchContext searchContext) {
		super();
		this.searchContext = searchContext;
		this.data = new XData().setType(XDataType.RESULT.toString()).add(new XField().setType(XFieldType.HIDDEN.toString()).setVar(XDataType.FORM_TYPE.toString()).add(new XValue(Search.XMLNS))).add(Field.class.cast(new XReported().add(reports)));
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Search search = protocol.cast(Search.class);
		context.write(search.clear().x(this.searchContext.search(search.getX(), this.data.clone(), XData.class)).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
