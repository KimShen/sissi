package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.ucenter.Field.FieldFinder;
import com.sissi.ucenter.Field.Fields;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.impl.MultiFields;

/**
 * @author kim 2013年12月5日
 */
public class RegisterStoreMultiProcessor extends RegisterStoreProcessor {

	public RegisterStoreMultiProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super(registerContext, iqTypeProcessor);
	}

	@Override
	protected Fields build(FieldFinder finder) {
		return new MultiFields(finder);
	}
}