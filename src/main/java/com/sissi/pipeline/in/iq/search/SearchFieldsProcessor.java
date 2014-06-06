package com.sissi.pipeline.in.iq.search;

import com.sissi.context.JIDContext;
import com.sissi.field.Fields;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.search.Search;

/**
 * 表单初始
 * 
 * @author kim 2013年12月3日
 */
public class SearchFieldsProcessor implements Input {

	private final Fields fields;

	public SearchFieldsProcessor(Fields fields) {
		super();
		this.fields = fields;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Search.class).clear().add(this.fields).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
