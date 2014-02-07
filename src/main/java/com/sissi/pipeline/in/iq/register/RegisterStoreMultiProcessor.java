package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQProcessor;
import com.sissi.protocol.iq.data.XData;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月5日
 */
public class RegisterStoreMultiProcessor extends RegisterStoreProcessor {

	public RegisterStoreMultiProcessor(RegisterContext registerContext, IQProcessor iqProcessor) {
		super(registerContext, iqProcessor);
	}

	@Override
	protected Fields process(Fields fields) {
		return new BeanFields(false, fields.findField(XData.NAME, XData.class).getFields());
	}
}