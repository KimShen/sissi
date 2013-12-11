package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.ucenter.Field.FieldFinder;
import com.sissi.ucenter.Field.Fields;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.impl.SimpleFields;

/**
 * @author kim 2013年12月3日
 */
public class RegisterStoreSimpleProcessor extends RegisterStoreProcessor {

	public RegisterStoreSimpleProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super(registerContext, iqTypeProcessor);
	}

	@Override
	protected Fields build(FieldFinder finder) {
		return new SimpleFields(finder);
	}
}
